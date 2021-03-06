package fr.almavivahealth.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Texture;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Repository
public interface TextureRepository extends JpaRepository<Texture, Long> {

	Optional<Texture> findByNameIgnoreCase(String name);

	List<Texture> findAllByOrderByIdAsc();

}
