package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@ManyToMany
	private List<Content> entries;

	@ManyToMany
	private List<Content> dishes;

	@ManyToMany
	private List<Content> desserts;

	@ManyToMany
	private List<Content> dairyProducts;

	@ManyToMany
	private List<Content> garnishes;

	private String moment;

	@ManyToOne
	private Patient patient;
}
