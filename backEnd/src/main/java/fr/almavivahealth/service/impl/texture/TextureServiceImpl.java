package fr.almavivahealth.service.impl.texture;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.service.TextureService;
import fr.almavivahealth.service.dto.TextureDTO;
import fr.almavivahealth.service.mapper.TextureMapper;

/**
 * Service Implementation for managing Texture.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class TextureServiceImpl implements TextureService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextureServiceImpl.class);

	private final TextureRepository textureRepository;

	private final TextureMapper textureMapper;

    @Autowired
	public TextureServiceImpl(final TextureRepository textureRepository, final TextureMapper textureMapper) {
		this.textureRepository = textureRepository;
		this.textureMapper = textureMapper;
	}

    /**
	 * Save a texture.
	 *
	 * @param textureDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public TextureDTO save(final TextureDTO textureDTO) {
		LOGGER.debug("Request to save Texture : {}", textureDTO);
		Texture texture = textureMapper.textureDTOToTexture(textureDTO);
		texture = textureRepository.save(texture);
		return textureMapper.textureToTextureDTO(texture);
	}

	/**
	 * Update a texture.
	 *
	 * @param textureDTO the texture DTO
	 * @return the persisted entity
	 */
	@Override
	public TextureDTO update(final TextureDTO textureDTO) {
		LOGGER.debug("Request to update Texture : {}", textureDTO);
		Texture texture = textureMapper.textureDTOToTexture(textureDTO);
		texture = textureRepository.saveAndFlush(texture);
		return textureMapper.textureToTextureDTO(texture);
	}

	/**
	 * Get all the textures.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TextureDTO> findAll() {
		LOGGER.debug("Request to get all Textures");
		return textureRepository.findAllByOrderByIdAsc().stream()
				.map(textureMapper::textureToTextureDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" texture.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TextureDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Texture : {}", id);
		return textureRepository.findById(id)
				.map(textureMapper::textureToTextureDTO);
	}

	/**
	 * Delete the "id" texture.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Texture : {}", id);
		textureRepository.deleteById(id);
	}
}
