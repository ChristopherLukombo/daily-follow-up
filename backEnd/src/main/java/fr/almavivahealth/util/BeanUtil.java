package fr.almavivahealth.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static <T> T getBean(final Class<T> beanClass) {
		return context.getBean(beanClass);
	}

}