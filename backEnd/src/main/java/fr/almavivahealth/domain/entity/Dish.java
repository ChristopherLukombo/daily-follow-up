package fr.almavivahealth.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* A dish.
*
 * @author christopher
 * @version 17
*/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Example: 25479
	@Column(name = "code")
	private Integer code;

	// Example: Jus d'ananas pour ananas appertisé au jus
	@Column(name = "name")
	private String name;

	// Example: produits céréaliers
	@Column(name = "group_name")
	private String groupName;

	// Example: pâtes, riz et céréales
	@Column(name = "sub_group_name")
	private String subGroupName;

	// Example: pâtes, riz et céréales cuits
	@Column(name = "sub_sub_group_name")
	private String subSubGroupName;

	// Example: 42,8
	@Column(name = "calories")
	private Double calories;

	// Example: 0,57
	@Column(name = "protein")
	private Double protein;

	// Example: 13
	@Column(name = "carbohydrate")
	private Double carbohydrate;

	// Example: 0,5
	@Column(name = "lipides")
	private Double lipids;

	// Example: 0,2
	@Column(name = "sugars")
	private Double sugars;

	// Example: 0,5
	@Column(name = "food_fibres")
	private Double foodFibres;

	// Example: 0,58
	@Column(name = "ag_saturates")
	private Double agSaturates;

	// Example: 0,73
	@Column(name = "salt")
	private Double salt;
}
