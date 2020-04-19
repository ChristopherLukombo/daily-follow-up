package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.PatientHistory;

@Repository
public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long>{

}
