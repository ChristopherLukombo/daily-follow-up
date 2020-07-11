package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Day;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Repository
public interface DayRepository extends JpaRepository<Day, Long>{

	List<Day> findAllByOrderByIdDesc();

}
