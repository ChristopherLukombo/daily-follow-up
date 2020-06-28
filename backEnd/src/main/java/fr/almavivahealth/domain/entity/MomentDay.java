package fr.almavivahealth.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author christopher A momentday.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class MomentDay implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Content entry;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Content dish;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Content garnish;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Content dairyProduct;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Content dessert;

}
