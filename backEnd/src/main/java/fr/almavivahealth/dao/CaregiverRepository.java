package fr.almavivahealth.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Caregiver;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {

	@Query("SELECT c "
			+ "FROM Caregiver c "
			+ "INNER JOIN c.user u "
			+ "WHERE u.status = true "
			+ "ORDER BY c.id DESC")
	List<Caregiver> findAllByOrderByIdDesc();

	@Query("SELECT c "
			+ "FROM Caregiver c, Floor f "
			+ "WHERE f.id = c.floor.id "
			+ "AND f.number = :number")
	List<Caregiver> findAllByFloorNumber(@Param("number") Integer number);

	@Query("SELECT c "
			+ "FROM Caregiver c "
			+ "INNER JOIN c.user u "
			+ "WHERE u.status = true AND u.id = :userId")
	Optional<Caregiver> findByUserId(@Param("userId") Long userId);
}
