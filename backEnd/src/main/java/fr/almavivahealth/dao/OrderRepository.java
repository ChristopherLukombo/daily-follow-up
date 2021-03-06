package fr.almavivahealth.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.almavivahealth.domain.entity.Order;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("SELECT order "
			+ "FROM Order order "
			+ "WHERE order.deliveryDate >= :startDate "
			+ "AND order.deliveryDate <= :endDate")
	List<Order> findAllForWeekBetween(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	List<Order> findAllByPatientId(Long patientId);

	@Query("SELECT order "
			+ "FROM Order order "
			+ "WHERE order.deliveryDate <= :date "
			+ "AND order.deliveryDate >= :date")
	List<Order> findAllOrdersBetween(
			@Param("date") LocalDate date);

	@Query("SELECT order "
			+ "FROM Order order "
			+ "WHERE order.deliveryDate = :date")
	List<Order> findAllOrdersForDate(
			@Param("date") LocalDate date);

	@Query("SELECT order "
			+ "FROM Order order "
			+ "WHERE order.patient.id = :patientId "
			+ "AND order.deliveryDate = :deliveryDate")
	List<Order> findAllByPatientIdAndMoment(
			@Param("patientId") Long patientId,
			@Param("deliveryDate") LocalDate deliveryDate);
}
