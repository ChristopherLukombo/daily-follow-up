package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Diet;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long>{

}
