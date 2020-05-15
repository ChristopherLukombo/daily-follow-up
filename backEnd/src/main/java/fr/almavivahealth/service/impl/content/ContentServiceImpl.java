package fr.almavivahealth.service.impl.content;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.service.ContentService;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import fr.almavivahealth.service.mapper.ContentMapper;

/**
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	private final ContentRepository contentRepository;

	private final ContentMapper contentMapper;

	@Autowired
	public ContentServiceImpl(final ContentRepository contentRepository, final ContentMapper contentMapper) {
		this.contentRepository = contentRepository;
		this.contentMapper = contentMapper;
	}

	/**
	 * Save a content.
	 *
	 * @param contentDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ContentDTO save(final ContentDTO contentDTO) {
		LOGGER.debug("Request to save Content : {}", contentDTO);
		Content content = contentMapper.contentDTOToContent(contentDTO);
		content = contentRepository.save(content);
		return contentMapper.contentToContentDTO(content);
	}

	/**
	 * Update a content.
	 *
	 * @param contentDTO the content DTO
	 * @return the persisted entity
	 */
	@Override
	public ContentDTO update(final ContentDTO contentDTO) {
		LOGGER.debug("Request to update Content : {}", contentDTO);
		Content content = contentMapper.contentDTOToContent(contentDTO);
		content = contentRepository.saveAndFlush(content);
		return contentMapper.contentToContentDTO(content);
	}

	/**
	 * Get all the contents.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ContentDTO> findAll() {
		LOGGER.debug("Request to get all Contents");
		return contentRepository.findAllByOrderByIdDesc().stream()
				.map(contentMapper::contentToContentDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" content.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ContentDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Content : {}", id);
		return contentRepository.findById(id)
				.map(contentMapper::contentToContentDTO);
	}

	/**
	 * Delete the "id" content.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Content : {}", id);
		contentRepository.deleteById(id);
	}

	/**
	 * Save all contents.
	 *
	 * @param contentList the content list
	 * @return the list of persisted entities
	 */
	@Override
	public List<ContentDTO> saveAll(final ContentList contentList) {
		LOGGER.debug("Request to save all contents");
		final List<Content> contents = contentList.getContents().stream()
				.map(contentMapper::contentDTOToContent)
				.collect(Collectors.toList());

		return contentRepository.saveAll(contents).stream()
				.map(contentMapper::contentToContentDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find the first 5 contents by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ContentDTO> findFirst5ByName(final String name) {
		LOGGER.debug("Request to get first 5 Contents by name : {}", name);
		return contentRepository.findByNameIgnoreCaseContaining(name, PageRequest.of(0, 5))
				.map(contentMapper::contentToContentDTO)
				.getContent();
	}
}
