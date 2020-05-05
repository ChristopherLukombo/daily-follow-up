package fr.almavivahealth.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import fr.almavivahealth.utils.BeanUtil;

@Component
public class AppContextProvider implements ApplicationContextAware {

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		BeanUtil.setApplicationContext(applicationContext);
	}

}
