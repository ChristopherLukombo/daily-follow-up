package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
