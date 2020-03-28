package fr.almavivahealth.service.mapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.User;
import fr.almavivahealth.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "pseudo", target = "pseudo"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "birthDay", target = "birthDay"),
            @Mapping(source = "role.name", target = "roleName"),
    })
    UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    User userDTOToUser(UserDTO userDTO);

}