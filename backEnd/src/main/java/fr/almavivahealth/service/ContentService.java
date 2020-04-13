package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.ContentDTO;

/**
 * The Interface ContentService.
 */
public interface ContentService {

	/**
	 * Save a content.
	 *
	 * @param contentDTO the entity to save
	 * @return the persisted entity
	 */
	ContentDTO save(ContentDTO contentDTO);

	/**
	 * Update a content.
	 *
	 * @param contentDTO the content DTO
	 * @return the persisted entity
	 */
	ContentDTO update(ContentDTO contentDTO);

	/**
	 * Get all the contents.
	 *
	 * @return the list of entities
	 */
	List<ContentDTO> findAll();

	/**
	 * Get the "id" content.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<ContentDTO> findOne(Long id);

	/**
	 * Delete the "id" content.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
