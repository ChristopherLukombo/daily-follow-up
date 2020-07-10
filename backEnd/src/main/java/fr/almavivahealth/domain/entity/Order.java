package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import fr.almavivahealth.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author christopher An order.
 */
@Entity
@Table(name = "\"order\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate deliveryDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@ManyToOne
	private Content entry;

	@ManyToOne
	private Content dish;

	@ManyToOne
	private Content dessert;

	@ManyToOne
	private Content dairyProduct;

	@ManyToOne
	private Content garnish;

	private String moment;

	@ManyToOne
	private Patient patient;

	@LastModifiedDate
	private LocalDate lastModifDate;

	@LastModifiedBy
	private String lastModifBy;
}
