package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fr.almavivahealth.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * An order.
 *
 * @author christopher
 * @version 16
 */
@Entity
@Table(name = "\"order\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
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

	@CreatedBy
	private String createdBy;

	@CreatedDate
	private LocalDate createdDate;

	@LastModifiedDate
	private LocalDate lastModifDate;

	@LastModifiedBy
	private String lastModifBy;
}
