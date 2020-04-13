package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Floor;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

	List<Floor> findAllByOrderByIdDesc();
	
}
