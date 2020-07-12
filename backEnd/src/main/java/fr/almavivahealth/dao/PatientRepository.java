package fr.almavivahealth.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.projection.PatientsByStatus;
import fr.almavivahealth.domain.projection.PatientsPerAllergy;
import fr.almavivahealth.domain.projection.PatientsPerDiet;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Patient> findAllByStateTrueOrderByIdDesc();

	List<Patient> findAllByStateFalseOrderByIdDesc();

	Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);

	@Query(value =
			  "SELECT "
			+ "DISTINCT UPPER(a.name) AS allergyName, "
			+ "COUNT(UPPER(a.name)) OVER(PARTITION BY UPPER( a.name))  AS numberPatients, "
			+ "( CAST ((COUNT(UPPER(a.name)) OVER(PARTITION BY UPPER( a.name))) AS NUMERIC)  / "
			+ "CAST ((select count(id) FROM patient p2 WHERE state = true) AS numeric)) * 100 AS percentage "
			+ "FROM patient_allergies pa "
			+ "INNER JOIN allergy a on a.id = pa.allergies_id "

			+ "INNER JOIN patient p on p.id = pa.patient_id WHERE p.state = true",
			nativeQuery = true)
	List<PatientsPerAllergy> findNumberOfPatientsPerAllergy();

	@Query(value = "SELECT "
			+ "DISTINCT UPPER(d.name) as dietName, "
			+ "COUNT(UPPER(d.name)) OVER(PARTITION BY UPPER( d.name)) AS numberPatients, "
			+ "( CAST ((COUNT(UPPER(d.name)) OVER(PARTITION BY UPPER( d.name))) AS numeric) / "
			+ "CAST ((SELECT count(id) FROM patient p2 WHERE state = true) AS numeric)) * 100 AS percentage "
			+ "FROM patient_diets pd "
			+ "INNER JOIN diet d on pd.diets_id = d.id "
			+ "INNER JOIN patient p on p.id = pd.patient_id WHERE p.state = true",
			nativeQuery = true)
	List<PatientsPerDiet> findNumberOfPatientsPerDiet();

	@Query(value = "SELECT * FROM "
			+ "(SELECT COUNT(*) AS activePatients FROM patient p WHERE state = true) AS a, "
			+ "(SELECT COUNT(*) AS inactivePatients FROM patient p WHERE state = false) AS b, "
			+ "(SELECT COUNT(*) AS totalPatients FROM patient p) AS c",
			nativeQuery = true)
	Optional<PatientsByStatus> findNumberOfPatientsByStatus();

	@Query("SELECT p "
			+ "FROM Floor f, Patient p "
			+ "JOIN TREAT(f.rooms AS Room) r "
			+ "WHERE f.number = :number "
			+ "AND r.id = p.room.id")
	List<Patient> findAllByFloorNumber(@Param("number") Integer number);

	@Query("SELECT p FROM "
			+ "Patient p "
			+ "JOIN TREAT(p.orders AS Order) o "
			+ "WHERE o.id = :orderId")
	Optional<Patient> findPatientByOrderId(@Param("orderId") Long orderId);
}
