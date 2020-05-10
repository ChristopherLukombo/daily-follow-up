package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Caregiver;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

	List<Caregiver> findAllByOrderByIdDesc();
	
}
