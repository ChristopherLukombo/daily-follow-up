package fr.almavivahealth.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.CaregiverHistory;

@Repository
public interface CaregiverHistoryRepository extends JpaRepository<CaregiverHistory, Long> {

	Page<CaregiverHistory> findAllByCaregiverIdOrderByModifiedDateDesc(Long caregiverHistory, Pageable pageable);
}
