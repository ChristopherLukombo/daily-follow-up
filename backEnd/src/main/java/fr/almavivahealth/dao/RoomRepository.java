package fr.almavivahealth.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	Optional<Room> findByNumberIgnoreCase(String number);

	@Query("SELECT patient.room FROM Patient patient JOIN patient.room WHERE patient.id = :patientId")
	Optional<Room> findByPatientId(@Param("patientId") Long patientId);

	List<Room> findAllByOrderByIdDesc();
	
	@Query("SELECT room FROM Room room WHERE size(room.patients) < room.maxCapacity")
	List<Room> findAllVacantRooms();
	
}
