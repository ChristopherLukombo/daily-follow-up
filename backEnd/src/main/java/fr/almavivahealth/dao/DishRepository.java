package fr.almavivahealth.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>{

	@Query("SELECT d FROM Dish d WHERE UPPER(unaccent(d.name)) LIKE UPPER(unaccent(CONCAT('%',:name,'%')))")
	Page<Dish> findByNameIgnoreCaseContaining(@Param("name") String name, Pageable pageable);

	Optional<Dish> findByName(String name);
}
