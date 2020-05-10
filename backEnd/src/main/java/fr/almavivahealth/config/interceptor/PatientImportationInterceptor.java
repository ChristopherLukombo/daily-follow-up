package fr.almavivahealth.config.interceptor;

import static fr.almavivahealth.constants.Constants.API_PATIENTS_IMPORT;
import static fr.almavivahealth.constants.ErrorMessage.ONE_OR_MORE_PATIENTS_ALREADY_EXIST;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientImportationAttempts;

@Component
public class PatientImportationInterceptor implements HandlerInterceptor  {

    private final PatientImportationAttempts patientImportationAttempts;

    private final MessageSource messageSource;

	@Autowired
	public PatientImportationInterceptor(
			final PatientImportationAttempts patientImportationAttempts,
			final MessageSource messageSource) {
		this.patientImportationAttempts = patientImportationAttempts;
		this.messageSource = messageSource;
	}


	/**
	 * After completion.
	 *
	 * @param request the request
	 * @param response the response
	 * @param handler the handler
	 * @param ex the ex
	 * @throws Exception the exception
	 */
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		final Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

		final String ip = request.getRemoteAddr();
		final String pseudo = request.getRemoteUser();
		final Locale locale = request.getLocale();

		if (request.getRequestURL().indexOf(API_PATIENTS_IMPORT) != -1 && throwable instanceof DailyFollowUpException) {
			final DailyFollowUpException dailyFollowUpException = (DailyFollowUpException) throwable;
			final Throwable rootCause = ExceptionUtils.getRootCause(dailyFollowUpException);
			if (messageSource.getMessage(ONE_OR_MORE_PATIENTS_ALREADY_EXIST, null, locale).equals(rootCause.getMessage())) {
				patientImportationAttempts.storeFailedAttempts(ip, pseudo);
			}
		} else if (request.getRequestURL().indexOf(API_PATIENTS_IMPORT) != -1 && HttpStatus.OK.value() == response.getStatus()) {
			patientImportationAttempts.validateAttempt(ip, pseudo);
		}
	}

}
