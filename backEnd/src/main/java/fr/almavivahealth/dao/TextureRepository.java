package fr.almavivahealth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Room;

@Repository
public interface TextureRepository extends JpaRepository<Room, Long> {

}
