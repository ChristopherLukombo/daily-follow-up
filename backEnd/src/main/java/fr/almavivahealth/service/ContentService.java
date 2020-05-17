package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;

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

	/**
	 * Save all contents.
	 *
	 * @param contentList the content list
	 * @return the list of persisted entities
	 */
	List<ContentDTO> saveAll(ContentList contentList);

	/**
	 * Find the first 5 contents by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<ContentDTO> findFirst5ByName(String name);

	/**
	 * Upload picture.
	 *
	 * @param file      the file
	 * @param contentId the content id
	 * @return the string
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	String uploadPicture(MultipartFile file, Long contentId) throws DailyFollowUpException;
}
