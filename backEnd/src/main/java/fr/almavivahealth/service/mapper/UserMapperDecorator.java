package fr.almavivahealth.service.mapper;

import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.service.dto.UserDTO;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * @author christopher
 * @version 16
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
            user.setImageUrl(userDTO.getImageUrl());
            user.setCreateDate(userDTO.getCreateDate());
            user.setStatus(userDTO.isStatus());
            user.setPassword(userDTO.getPassword());
            final Role role = buildRole(userDTO);
			user.setRole(role);
			user.setHasChangedPassword(userDTO.getHasChangedPassword());
            return user;
        }
    }

	private Role buildRole(final UserDTO userDTO) {
		final String roleName = userDTO.getRoleName();
		return Role.builder()
				.name(roleName)
				.build();
	}
}