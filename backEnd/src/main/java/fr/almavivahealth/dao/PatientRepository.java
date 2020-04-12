package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Patient> findAllByStateTrueOrderByIdDesc();

}
