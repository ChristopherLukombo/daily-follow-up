package fr.almavivahealth.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Content;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	List<Content> findAllByOrderByIdDesc();

	Optional<Content> findByNameIgnoreCase(String name);
}
