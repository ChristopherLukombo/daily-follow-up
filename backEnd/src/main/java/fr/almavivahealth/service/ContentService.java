package fr.almavivahealth.service;

import java.util.List;
import java.util.Locale;
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
	 * Upload picture.
	 *
	 * @param file the file
	 * @param contentId the content id
	 * @param locale the locale
	 * @return the string
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	String uploadPicture(final MultipartFile file, final Long contentId, final Locale locale) throws DailyFollowUpException;


	/**
	 * Find picture.
	 *
	 * @param contentId the content id
	 * @return the byte[]
	 */
	byte[] findPicture(Long contentId);

	/**
	 * Delete by ids.
	 *
	 * @param ids the ids
	 */
	void deleteByIds(List<Long> ids);
}
