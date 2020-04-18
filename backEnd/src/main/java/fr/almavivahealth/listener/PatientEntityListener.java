package fr.almavivahealth.listener;

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
		if (target.getState() != null && target.getState()) {
			perform(target, Action.UPDATED);
		} else {
			perform(target, Action.DELETED);
		}
	}

	private void perform(final Patient target, final Action action) {
		final EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(new PatientHistory(target, action));
	}

}
