package fr.almavivahealth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	Optional<Room> findByNumberIgnoreCase(String number);
	
}
