package fr.almavivahealth.web.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * ResponseEntityExceptionHandler for handle exception and launch error with
 * custom status.
 * 
 * @author christopher
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { DailyFollowUpException.class })
	protected ResponseEntity<Object> handleDailyFollowUpException(final DailyFollowUpException ex,
			final WebRequest request) {

		return handleExceptionInternal(ex, ex.getErrorMessage(), new HttpHeaders(),
				HttpStatus.valueOf(ex.getErrorCode()), request);
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

}