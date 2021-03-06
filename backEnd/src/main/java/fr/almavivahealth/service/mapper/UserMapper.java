package fr.almavivahealth.service.mapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * @author christopher
 * @version 17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "pseudo", target = "pseudo")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "createDate", target = "createDate")
	@Mapping(source = "status", target = "status")
	@Mapping(source = "imageUrl", target = "imageUrl")
	@Mapping(source = "role.name", target = "roleName")
	@Mapping(source = "hasChangedPassword", target = "hasChangedPassword")
	UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    User userDTOToUser(UserDTO userDTO);
}