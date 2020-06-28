package fr.almavivahealth.domain.listener;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.domain.entity.Caregiver;
import fr.almavivahealth.domain.entity.CaregiverHistory;
import fr.almavivahealth.domain.enums.Action;
import fr.almavivahealth.util.BeanUtil;

@Transactional
public class CaregiverEntityListener {

	@PrePersist
	public void prePersist(final Caregiver target) {
		perform(target, Action.INSERTED);
	}

	@PreUpdate
	public void preUpdate(final Caregiver target) {
		perform(target, Action.UPDATED);
	}

	@PreDestroy
	public void preDelete(final Caregiver target) {
		perform(target, Action.DELETED);
	}

	private void perform(final Caregiver target, final Action action) {
		final EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(new CaregiverHistory(target, action));
	}
}
