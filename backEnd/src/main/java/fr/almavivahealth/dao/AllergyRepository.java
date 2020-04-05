package fr.almavivahealth.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Allergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

	List<Allergy> findAllByNameIgnoreCaseIn(Set<String> names);
}
