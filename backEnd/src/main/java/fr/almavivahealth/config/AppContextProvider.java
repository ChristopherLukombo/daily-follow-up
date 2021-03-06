package fr.almavivahealth.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import fr.almavivahealth.util.BeanUtil;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Component
public class AppContextProvider implements ApplicationContextAware {

	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		BeanUtil.setApplicationContext(applicationContext);
	}

}
