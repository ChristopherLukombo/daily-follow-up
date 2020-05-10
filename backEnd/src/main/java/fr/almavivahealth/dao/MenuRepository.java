package fr.almavivahealth.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findAllByOrderByIdDesc();
	
	@Query("SELECT menu FROM Menu menu "
			+ "WHERE menu.texture.name = :textureName "
			+ "AND menu.diet.name IN :dietNames "
			+ "AND menu.startDate  >= :startDate AND menu.endDate <= :endDate")
	List<Menu> findAllByWeek(
			@Param("textureName") String textureName,
			@Param("dietNames") Set<String> dietNames,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
	
}
