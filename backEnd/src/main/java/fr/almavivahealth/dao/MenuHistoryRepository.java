package fr.almavivahealth.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.MenuHistory;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Repository
public interface MenuHistoryRepository extends JpaRepository<MenuHistory, Long> {

	Page<MenuHistory> findAllByMenuIdOrderByModifiedDateDesc(Long menuId, Pageable pageable);
}
