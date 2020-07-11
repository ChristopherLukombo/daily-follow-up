package fr.almavivahealth.service.impl.content;

import static fr.almavivahealth.constants.Constants.SLASH;
import static fr.almavivahealth.constants.ErrorMessage.AN_ERROR_OCCURRED_WHILE_TRYING_TO_UPLOAD_THE_FILE;
import static fr.almavivahealth.constants.ErrorMessage.ID_CONTENT_DOES_NOT_EXIST;
import static fr.almavivahealth.util.functional.FunctionWithException.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.ContentService;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import fr.almavivahealth.service.mapper.ContentMapper;
import fr.almavivahealth.service.propeties.ContentProperties;

/**
 * Service Implementation for managing Content.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	private final ContentRepository contentRepository;

	private final ContentMapper contentMapper;

	private final ContentProperties contentProperties;

	private final MessageSource messageSource;

    @Autowired
	public ContentServiceImpl(
			final ContentRepository contentRepository,
			final ContentMapper contentMapper,
			final ContentProperties contentProperties,
			final MessageSource messageSource) {
		this.contentRepository = contentRepository;
		this.contentMapper = contentMapper;
		this.contentProperties = contentProperties;
		this.messageSource = messageSource;
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
	 * Upload picture.
	 *
	 * @param file the file
	 * @param contentId the content id
	 * @param locale    the locale
	 * @return the string
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public String uploadPicture(final MultipartFile file, final Long contentId, final Locale locale)
			throws DailyFollowUpException {
		LOGGER.debug("Request to upload profile picture : {}", contentId);
		return contentRepository.findById(contentId)
				.map(wrapper(content -> storePicture(content, file, contentId, locale)))
				.orElseThrow(() -> new DailyFollowUpException(
						messageSource.getMessage(ID_CONTENT_DOES_NOT_EXIST, null, locale)));
	}

	private String storePicture(
			final Content content,
			final MultipartFile file,
			final Long contentId,
			final Locale locale) throws DailyFollowUpException {
		try {
			final String pathName = contentProperties.getImagesPath() + contentId;
			createDirectory(pathName); // create directory if not exist
			sendFileToFolder(file, Paths.get(pathName));
			content.setImageUrl(file.getOriginalFilename());
			return file.getOriginalFilename();
		} catch (final IOException e) {
			throw new DailyFollowUpException(
					messageSource.getMessage(AN_ERROR_OCCURRED_WHILE_TRYING_TO_UPLOAD_THE_FILE, null, locale), e);
		}
	}

	private void createDirectory(final String pathName) throws IOException {
		final Path path = Paths.get(pathName);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	private void sendFileToFolder(final MultipartFile file, final Path path) throws IOException {
		final Path resolve = path.resolve(file.getOriginalFilename());
		final InputStream inputStream = file.getInputStream();
		Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Find picture.
	 *
	 * @param contentId the content id
	 * @return the byte[]
	 */
	@Override
	@Transactional(readOnly = true)
	public byte[] findPicture(final Long contentId) {
		LOGGER.debug("Request to get picture : {}", contentId);
		return contentRepository.findById(contentId)
				.map(Content::getImageUrl)
				.map(imageUrl -> readFile(contentId, imageUrl))
				.orElseGet(() -> ArrayUtils.EMPTY_BYTE_ARRAY);
	}

	private byte[] readFile(final Long contentId, final String imageUrl) {
		try {
			final String pathName = fetchPathName(contentId, imageUrl);
			return Files.readAllBytes(new File(pathName).toPath());
		} catch (final IOException e) {
			return ArrayUtils.EMPTY_BYTE_ARRAY;
		}
	}

	private String fetchPathName(final Long contentId, final String imageUrl) {
		return new StringBuilder()
				.append(contentProperties.getImagesPath())
				.append(contentId)
				.append(SLASH)
				.append(imageUrl)
				.toString();
	}

	/**
	 * Delete by ids.
	 *
	 * @param ids the ids
	 */
	@Override
	public void deleteByIds(final List<Long> ids) {
		for (final Long id : ids) {
			contentRepository.deleteById(id);
		}
	}
}
