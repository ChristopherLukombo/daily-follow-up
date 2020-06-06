package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Caregiver;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

	List<Caregiver> findAllByOrderByIdDesc();

	@Query("SELECT c "
			+ "FROM Caregiver c, Floor f "
			+ "WHERE f.id = c.floor.id "
			+ "AND f.number = :number")
	List<Caregiver> findAllByFloorNumber(@Param("number") Integer number);
}
