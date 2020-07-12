package fr.almavivahealth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import fr.almavivahealth.config.interceptor.PatientImportationInterceptor;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final PatientImportationInterceptor patientImportationInterceptor;

	@Autowired
	public WebMvcConfig(final PatientImportationInterceptor patientImportationInterceptor) {
		this.patientImportationInterceptor = patientImportationInterceptor;
	}

	/**
	 * Locale resolver.
	 *
	 * @return the locale resolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	/**
	 * Locale interceptor.
	 *
	 * @return the locale change interceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeInterceptor() {
		final LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		return localeInterceptor;
	}

	/**
	 * Adds the interceptors.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(localeInterceptor());
		registry.addInterceptor(patientImportationInterceptor);
	}

}
