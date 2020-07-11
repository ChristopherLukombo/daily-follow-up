package fr.almavivahealth.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.TopTrendyMenu;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findAllByOrderByIdAsc();

	@Query("SELECT menu "
			+ "FROM Menu menu "
			+ "WHERE menu.startDate <= :currentDate "
			+ "AND menu.endDate >= :currentDate")
	List<Menu> findCurrentMenus(
			@Param("currentDate") LocalDate currentDate);

	@Query(value = "SELECT COUNT(*) as nb, m.texture AS texture, md.diets AS diets "
			+ "FROM menu m "
			+ "INNER JOIN menu_diets md "
			+ "ON m.id = md.menu_id "
			+ "GROUP BY md.diets, m.texture "
			+ "ORDER BY nb DESC limit 5", nativeQuery = true)
	List<TopTrendyMenu> findTrendyDishes();

	@Query("SELECT menu "
			+ "FROM Menu menu "
			+ "WHERE menu.startDate > :date")
	List<Menu> findFutureMenus(@Param("date") LocalDate date);

	@Query("SELECT menu "
			+ "FROM Menu menu "
			+ "WHERE menu.endDate < :date")
	List<Menu> findPastMenus(@Param("date") LocalDate date);

}
