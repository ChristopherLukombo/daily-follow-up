package fr.almavivahealth.domain.listener;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.MenuHistory;
import fr.almavivahealth.domain.enums.Action;
import fr.almavivahealth.util.BeanUtil;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Transactional
public class MenuEntityListener {

	@PrePersist
	public void prePersist(final Menu target) {
		perform(target, Action.INSERTED);
	}

	@PreUpdate
	public void preUpdate(final Menu target) {
		perform(target, Action.UPDATED);
	}

	@PreDestroy
	public void preDelete(final Menu target) {
		perform(target, Action.DELETED);
	}

	private void perform(final Menu target, final Action action) {
		final EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(new MenuHistory(target, action));
	}
}
