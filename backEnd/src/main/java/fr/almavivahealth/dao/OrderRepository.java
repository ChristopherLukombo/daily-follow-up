package fr.almavivahealth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findAllByOrderByIdDesc();
	
}
