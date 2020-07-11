package fr.almavivahealth.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.User;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByPseudoIgnoreCase(String pseudo);

	@Modifying
	@Query("UPDATE User u SET u.imageUrl = :imageUrl WHERE u.id = :userId")
	void setImageUrl(@Param("imageUrl") String imageUrl, @Param("userId") Long userId);

	List<User> findAllByStatusTrueOrderByIdDesc();

    List<User> findAllByStatusIsTrueAndLastLoginDateBefore(LocalDateTime localDateTime);
}
