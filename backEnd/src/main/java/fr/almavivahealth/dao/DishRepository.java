package fr.almavivahealth.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>{

	Page<Dish> findByNameIgnoreCaseContaining(String name, Pageable pageable);

	Optional<Dish> findByCode(Integer code);
}
