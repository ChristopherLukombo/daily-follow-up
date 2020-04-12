package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	List<Content> findAllByOrderByIdDesc();
	
}
