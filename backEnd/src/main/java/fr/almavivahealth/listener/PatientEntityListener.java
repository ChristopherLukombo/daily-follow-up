package fr.almavivahealth.listener;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.PatientHistory;
import fr.almavivahealth.enums.Action;
import fr.almavivahealth.util.BeanUtil;

@Transactional
public class PatientEntityListener {

	@PrePersist
	public void prePersist(final Patient target) {
		perform(target, Action.INSERTED);
	}

	@PreUpdate
	public void preUpdate(final Patient target) {
		final boolean stateOfPatient = findStateOfPatient(target);
		final boolean stateOfPreviousPatient = findStateOfPreviousPatient(target);
		
		if (stateOfPreviousPatient && stateOfPatient) {
			perform(target, Action.UPDATED);
		} else if (!stateOfPreviousPatient && stateOfPatient) {
			perform(target, Action.RECREATED);
		} else {
			perform(target, Action.DELETED);
		}
	}

	private void perform(final Patient target, final Action action) {
		final EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(new PatientHistory(target, action));
	}
	
	private boolean findStateOfPatient(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getState)
				.orElseGet(() -> false);
	}
	
	private boolean findStateOfPreviousPatient(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getPreviousPatient)
				.map(Patient::getState)
				.orElseGet(() -> false);
	}

}
