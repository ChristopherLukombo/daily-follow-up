package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Caregiver;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

}
