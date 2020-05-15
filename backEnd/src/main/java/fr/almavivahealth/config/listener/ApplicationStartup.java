package fr.almavivahealth.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.almavivahealth.service.ContentImportationService;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	private final ContentImportationService dishImportationService;

    @Autowired
	public ApplicationStartup(final ContentImportationService dishImportationService) {
		this.dishImportationService = dishImportationService;
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		if (!dishImportationService.hasElements()) {
			dishImportationService.importContents();
		}
	}
}
