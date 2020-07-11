package fr.almavivahealth.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import fr.almavivahealth.util.BeanUtil;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Component
public class AppContextProvider implements ApplicationContextAware {

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		BeanUtil.setApplicationContext(applicationContext);
	}

}
