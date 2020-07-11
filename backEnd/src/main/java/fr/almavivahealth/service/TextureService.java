package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.TextureDTO;

/**
 * The Interface TextureService.
 * @author christopher
 * @version 16
 */
public interface TextureService {

	/**
	 * Save a texture.
	 *
	 * @param textureDTO the entity to save
	 * @return the persisted entity
	 */
	TextureDTO save(TextureDTO textureDTO);

	/**
	 * Update a texture.
	 *
	 * @param textureDTO the texture DTO
	 * @return the persisted entity
	 */
	TextureDTO update(TextureDTO textureDTO);

	/**
	 * Get all the textures.
	 *
	 * @return the list of entities
	 */
	List<TextureDTO> findAll();

	/**
	 * Get the "id" texture.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<TextureDTO> findOne(Long id);

	/**
	 * Delete the "id" texture.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
