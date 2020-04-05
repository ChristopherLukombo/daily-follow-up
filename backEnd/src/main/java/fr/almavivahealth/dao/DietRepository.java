package fr.almavivahealth.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Diet;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long>{
	
	List<Diet> findAllByNameIgnoreCaseIn(Set<String> names);
}
