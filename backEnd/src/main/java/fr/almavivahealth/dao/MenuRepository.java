package fr.almavivahealth.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findAllByOrderByIdAsc();

	@Query("SELECT menu FROM Menu menu "
			+ "INNER JOIN menu.diets md "
			+ "WHERE md IN :dietNames "
			+ "AND menu.texture = :textureName "
			+ "AND menu.startDate >= :startDate "
			+ "AND menu.endDate <= :endDate")
	List<Menu> findAllByWeek(
			@Param("textureName") String textureName,
			@Param("dietNames") List<String> dietNames,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query("SELECT menu "
			+ "FROM Menu menu "
			+ "WHERE menu.startDate <= :currentDate "
			+ "AND menu.endDate >= :currentDate")
	List<Menu> findCurrentMenus(
			@Param("currentDate") LocalDate currentDate);
}
