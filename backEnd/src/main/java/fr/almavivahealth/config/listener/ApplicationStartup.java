package fr.almavivahealth.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.almavivahealth.service.DishImportationService;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	private final DishImportationService dishImportationService;

    @Autowired
	public ApplicationStartup(final DishImportationService dishImportationService) {
		this.dishImportationService = dishImportationService;
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		if (!dishImportationService.hasElements()) {
			dishImportationService.importDishes();
		}
	}
}
