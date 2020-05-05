package fr.almavivahealth.utils;

import org.springframework.context.ApplicationContext;

/**
 * The Class BeanUtil.
 */
public final class BeanUtil {

	private static ApplicationContext context;

	// Private constructor to prevent instantiation
	private BeanUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 */
	public static void setApplicationContext(final ApplicationContext applicationContext) {
		context = applicationContext;
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param beanClass the bean class
	 * @return the bean
	 */
	public static <T> T getBean(final Class<T> beanClass) {
		return context.getBean(beanClass);
	}

}
