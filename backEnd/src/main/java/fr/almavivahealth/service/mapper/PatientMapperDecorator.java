package fr.almavivahealth.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.almavivahealth.domain.Address;
import fr.almavivahealth.domain.Allergy;
import fr.almavivahealth.domain.Comment;
import fr.almavivahealth.domain.Diet;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.service.dto.AddressDTO;
import fr.almavivahealth.service.dto.AllergyDTO;
import fr.almavivahealth.service.dto.CommentDTO;
import fr.almavivahealth.service.dto.DietDTO;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.dto.TextureDTO;

/**
 * Mapper for the entity Patient and its DTO called PatientDTO.
 *
 * @author christopher
 */
public abstract class PatientMapperDecorator implements PatientMapper {

	@Override
	public PatientDTO patientToPatientDTO(final Patient patient) {
		if (patient == null) {
			return null;
		}
		return PatientDTO.builder()
				.id(patient.getId())
				.firstName(patient.getFirstName())
				.lastName(patient.getLastName())
				.email(patient.getEmail())
				.situation(patient.getSituation())
				.dateOfBirth(patient.getDateOfBirth())
				.address(buildAddressDTO(patient))
				.phoneNumber(patient.getPhoneNumber())
				.mobilePhone(patient.getMobilePhone())
				.job(patient.getJob())
				.bloodGroup(patient.getBloodGroup())
				.height(patient.getHeight())
				.weight(patient.getWeight())
				.sex(patient.getSex())
				.state(patient.getState())
				.texture(buildTextureDTO(patient))
				.diets(buildDietsDTO(patient))
				.allergies(buildAllergiesDTO(patient))
				.comment(buildCommentDTO(patient))
				.roomId(buildRoomDTO(patient))
				.build();
	}

	private TextureDTO buildTextureDTO(final Patient patient) {
		if (patient.getTexture() == null) {
			return null;
		}
		return TextureDTO.builder()
				.id(patient.getTexture().getId())
				.name(patient.getTexture().getName())
				.build();
	}

	private AddressDTO buildAddressDTO(final Patient patient) {
		if (patient.getAddress() == null) {
			return null;
		}
		return AddressDTO.builder()
				.id(patient.getAddress().getId())
				.streetName(patient.getAddress().getStreetName())
				.city(patient.getAddress().getCity())
				.postalCode(patient.getAddress().getPostalCode())
				.build();
	}

	private List<DietDTO> buildDietsDTO(final Patient patient) {
		if (patient.getDiets() == null || patient.getDiets().isEmpty()) {
			return Collections.emptyList();
		}
		final List<DietDTO> dietsDTO = new ArrayList<>();
		for (final Diet diet : patient.getDiets()) {
			final DietDTO dietDTO = DietDTO.builder()
					.id(diet.getId())
					.name(diet.getName())
					.build();
			dietsDTO.add(dietDTO);
		}
		return dietsDTO;
	}

	private List<AllergyDTO> buildAllergiesDTO(final Patient patient) {
		if (patient.getAllergies() == null || patient.getAllergies().isEmpty()) {
			return Collections.emptyList();
		}
		final List<AllergyDTO> allergiesDTO = new ArrayList<>();
		for (final Allergy allergy : patient.getAllergies()) {
			final AllergyDTO allergyDTO = AllergyDTO.builder()
					.id(allergy.getId())
					.name(allergy.getName())
					.build();
			allergiesDTO.add(allergyDTO);
		}
		return allergiesDTO;
	}

	private CommentDTO buildCommentDTO(final Patient patient) {
		if (patient.getComment() == null) {
			return null;
		}
		return CommentDTO.builder()
				.id(patient.getId())
				.content(patient.getComment().getContent())
				.pseudo(patient.getComment().getPseudo())
				.lastModification(patient.getComment().getLastModification())
				.build();
	}

	private Long buildRoomDTO(final Patient patient) {
		final Room room = patient.getRoom();
		if (room == null) {
			return null;
		}
		return room.getId();
	}

	@Override
	public Patient patientDTOToPatient(final PatientDTO patientDTO) {
		if (patientDTO == null) {
			return null;
		}
		return Patient.builder()
				.id(patientDTO.getId())
				.firstName(patientDTO.getFirstName())
				.lastName(patientDTO.getLastName())
				.email(patientDTO.getEmail())
				.situation(patientDTO.getSituation())
				.dateOfBirth(patientDTO.getDateOfBirth())
				.address(this.buildAddress(patientDTO))
				.phoneNumber(patientDTO.getPhoneNumber())
				.mobilePhone(patientDTO.getMobilePhone())
				.job(patientDTO.getJob())
				.bloodGroup(patientDTO.getBloodGroup())
				.height(patientDTO.getHeight())
				.weight(patientDTO.getWeight())
				.sex(patientDTO.getSex())
				.state(patientDTO.getState())
				.texture(buildTexture(patientDTO))
				.diets(this.buildDiets(patientDTO))
				.allergies(this.buildAllergies(patientDTO))
				.comment(this.buildComment(patientDTO))
				.room(this.buildRoom(patientDTO))
				.build();
	}

	private Texture buildTexture(final PatientDTO patientDTO) {
		if (patientDTO.getTexture() == null) {
			return null;
		}
		return Texture.builder()
				.id(patientDTO.getTexture().getId())
				.name(patientDTO.getTexture().getName())
				.build();
	}

	private Address buildAddress(final PatientDTO patientDTO) {
		if (patientDTO.getAddress() == null) {
			return null;
		}
		return Address.builder()
				.id(patientDTO.getAddress().getId())
				.streetName(patientDTO.getAddress().getStreetName())
				.city(patientDTO.getAddress().getCity())
				.postalCode(patientDTO.getAddress().getPostalCode())
				.build();
	}

	private List<Diet> buildDiets(final PatientDTO patientDTO) {
		if (patientDTO.getDiets() == null || patientDTO.getDiets().isEmpty()) {
			return Collections.emptyList();
		}
		final List<Diet> diets = new ArrayList<>();
		for (final DietDTO dietDTO : patientDTO.getDiets()) {
			final Diet diet = Diet.builder()
					.id(dietDTO.getId())
					.name(dietDTO.getName())
					.build();
			diets.add(diet);
		}
		return diets;
	}

	private List<Allergy> buildAllergies(final PatientDTO patientDTO) {
		if (patientDTO.getAllergies() == null || patientDTO.getAllergies().isEmpty()) {
			return Collections.emptyList();
		}
		final List<Allergy> allergies = new ArrayList<>();
		for (final AllergyDTO allergyDTO : patientDTO.getAllergies()) {
			final Allergy allergy = Allergy.builder()
					.id(allergyDTO.getId())
					.name(allergyDTO.getName())
					.build();
			allergies.add(allergy);
		}
		return allergies;
	}

	private Comment buildComment(final PatientDTO patientDTO) {
		if (patientDTO.getComment() == null) {
			return null;
		}
		return Comment.builder()
				.id(patientDTO.getId())
				.content(patientDTO.getComment().getContent())
				.pseudo(patientDTO.getComment().getPseudo())
				.lastModification(patientDTO.getComment().getLastModification())
				.build();
	}

	private Room buildRoom(final PatientDTO patientDTO) {
		if (patientDTO.getRoomId() == null) {
			return null;
		}
		return Room.builder()
				.id(patientDTO.getRoomId())
				.build();
	}
}
