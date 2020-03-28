package fr.almavivahealth.service.mapper;

import fr.almavivahealth.domain.User;
import fr.almavivahealth.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * @author christopher
 */
public abstract class UserMapperDecorator implements UserMapper {

    @Override
	public UserDTO userToUserDTO(final User user) {
        return new UserDTO(user);
    }

    @Override
	public User userDTOToUser(final UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            final User user = new User();
            user.setId(userDTO.getId());
            user.setPseudo(userDTO.getPseudo());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setStatus(userDTO.isStatus());
            return user;
        }
    }
}