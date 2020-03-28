package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

}
