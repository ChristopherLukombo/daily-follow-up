package fr.almavivahealth.web.handler;

import static fr.almavivahealth.constants.ErrorMessage.ERROR_CONTENT_UNIQUE_NAME;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.exception.DetailedErrorApi;
import fr.almavivahealth.exception.ErrorApi;
import fr.almavivahealth.exception.FunctionalException;

/**
 * ResponseEntityExceptionHandler for handle exception and launch error with
 * custom status.
 *
 * @author christopher
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String EUROPE_PARIS = "Europe/Paris";

	private static final Map<String, String> constraintCodeMap = new HashMap<>();

	static {
		constraintCodeMap.put("content_unique_name_idx", ERROR_CONTENT_UNIQUE_NAME);
	}

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = { DailyFollowUpException.class })
	protected ResponseEntity<Object> handleDailyFollowUpException(final DailyFollowUpException ex,
			final WebRequest request) {
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		final DetailedErrorApi detailedErrorApi = new DetailedErrorApi(ex.getErrorMessage(),
				rootCause != null ? rootCause.getMessage() : StringUtils.EMPTY,
						HttpStatus.valueOf(ex.getErrorCode()), ZonedDateTime.now(ZoneId.of(EUROPE_PARIS)));
		return handleExceptionInternal(ex, detailedErrorApi, new HttpHeaders(), HttpStatus.valueOf(ex.getErrorCode()), request);
	}

	@ExceptionHandler(value = { FunctionalException.class })
	protected ResponseEntity<Object> handleFunctionWithException(final FunctionalException ex,
			final WebRequest request) {
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		final ErrorApi errorApi = new ErrorApi(
				rootCause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
				ZonedDateTime.now(ZoneId.of(EUROPE_PARIS)));
		return handleExceptionInternal(ex, errorApi, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		final Map<String, Set<String>> errorsMap = fieldErrors.stream()
				.collect(Collectors.groupingBy(FieldError::getField,
						Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
		return new ResponseEntity<>(errorsMap.isEmpty() ? ex : errorsMap, headers, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException ex) {
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		final String rootMsg = rootCause.getMessage();
		String message = null;

		if (rootMsg != null) {
			final Optional<Map.Entry<String, String>> entry = constraintCodeMap.entrySet().stream()
					.filter(it -> rootMsg.contains(it.getKey()))
					.findAny();
			if (entry.isPresent()) {
				message = messageSource.getMessage(entry.get().getValue(), null, LocaleContextHolder.getLocale());
			}
		}

		final ErrorApi errorApi = new ErrorApi(
				message != null ? message : rootCause.getMessage(),
						HttpStatus.CONFLICT,
						ZonedDateTime.now(ZoneId.of(EUROPE_PARIS)));

		return new ResponseEntity<>(errorApi, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({ DataAccessException.class })
	protected ResponseEntity<Object> handleDataAccessException(final DataAccessException ex, final WebRequest request) {
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		final ErrorApi errorApi = new ErrorApi(
				rootCause.getMessage(), HttpStatus.CONFLICT,
				ZonedDateTime.now(ZoneId.of(EUROPE_PARIS)));
		return handleExceptionInternal(ex, errorApi, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		final List<String> errors = new ArrayList<>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);
		final ErrorApi errorApi = new ErrorApi(
				rootCause.getMessage(), HttpStatus.CONFLICT,
				ZonedDateTime.now(ZoneId.of(EUROPE_PARIS)));
		return handleExceptionInternal(ex, errorApi, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

}
