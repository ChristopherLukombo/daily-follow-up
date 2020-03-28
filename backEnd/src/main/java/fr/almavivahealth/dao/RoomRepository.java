package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.almavivahealth.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
