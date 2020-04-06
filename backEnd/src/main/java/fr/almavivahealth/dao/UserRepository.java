package fr.almavivahealth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByPseudoIgnoreCase(String pseudo);

}
