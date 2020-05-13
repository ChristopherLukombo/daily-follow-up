package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.service.dto.TextureDTO;
import fr.almavivahealth.service.impl.texture.TextureServiceImpl;
import fr.almavivahealth.service.mapper.TextureMapper;

@RunWith(MockitoJUnitRunner.class)
public class TextureServiceTest {

	private static final long ID = 1L;
	
	private static final String NAME = "TEST";

	@Mock
	private TextureRepository textureRepository;

	@Mock
	private TextureMapper textureMapper;
	
	@InjectMocks
	private TextureServiceImpl textureServiceImpl;
	
	private static Texture getTexture() {
		return Texture.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	private static TextureDTO getTextureDTO() {
		return TextureDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	@Test
	public void shouldSaveTextureWhenIsOK() {
		// Given
		final Texture texture = getTexture();
		final TextureDTO textureDTO = getTextureDTO();

		// When
		when(textureRepository.save((Texture) any())).thenReturn(texture);
		when(textureMapper.textureToTextureDTO((Texture) any())).thenReturn(textureDTO);
		
		// Then
		assertThat(textureServiceImpl.save(textureDTO)).isNotNull();
	}
	
	@Test
	public void shouldSaveTextureWhenIsKO() {
		// Given
		final Texture texture = null;
		final TextureDTO textureDTO = null;

		// When
		when(textureRepository.save((Texture) any())).thenReturn(texture);
		when(textureMapper.textureToTextureDTO((Texture) any())).thenReturn(textureDTO);
		
		// Then
		assertThat(textureServiceImpl.save(textureDTO)).isNull();
	}
	
	@Test
	public void shouldUpdateTextureWhenIsOK() {
		// Given
		final Texture texture = getTexture();
		final TextureDTO textureDTO = getTextureDTO();

		// When
		when(textureRepository.saveAndFlush((Texture) any())).thenReturn(texture);
		when(textureMapper.textureToTextureDTO((Texture) any())).thenReturn(textureDTO);
		
		// Then
		assertThat(textureServiceImpl.update(textureDTO)).isNotNull();
	}
	
	@Test
	public void shouldUpdateTextureWhenIsKO() {
		// Given
		final Texture texture = null;
		final TextureDTO textureDTO = null;

		// When
		when(textureRepository.saveAndFlush((Texture) any())).thenReturn(texture);
		when(textureMapper.textureToTextureDTO((Texture) any())).thenReturn(textureDTO);
		
		// Then
		assertThat(textureServiceImpl.update(textureDTO)).isNull();
	}
	
	@Test
	public void shouldGetAllTexturesWhenIsOK() {
		// Given
		final List<Texture> textures = Arrays.asList(getTexture());
		
		// When
		when(textureRepository.findAllByOrderByIdDesc()).thenReturn(textures);
		
		// Then
		assertThat(textureServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllTexturesWhenIsEmpty() {
		// Given
		final List<Texture> textures = Collections.emptyList();
		
		// When
		when(textureRepository.findAllByOrderByIdDesc()).thenReturn(textures);
		
		// Then
		assertThat(textureServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllTexturesWhenIsNull() {
		// Given
		final List<Texture> textures = null;
		
		// When
		when(textureRepository.findAllByOrderByIdDesc()).thenReturn(textures);
		
		// Then
		assertThatThrownBy(() -> textureServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetTextureWhenIsOk() {
		// Given
		final Texture texture = getTexture();
		final TextureDTO textureDTO = getTextureDTO();

		// When
		when(textureRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(texture));
		when(textureMapper.textureToTextureDTO((Texture) any())).thenReturn(textureDTO);
		
		// Then
		assertThat(textureServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(textureDTO));
	}
	
	@Test
	public void shouldGetTextureWhenIsNull() {
		// Given
		final Texture texture = null;
		final TextureDTO textureDTO = null;

		// When
		when(textureRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(texture));
		
		// Then
		assertThat(textureServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(textureDTO));
	}
	
	@Test
	public void shouldDeleteWhenIsOk() {
		// When
		doNothing().when(textureRepository).deleteById(ID);
       
		// Then
		textureServiceImpl.delete(ID);
		
		verify(textureRepository, times(1)).deleteById(anyLong());
	}
	
}
