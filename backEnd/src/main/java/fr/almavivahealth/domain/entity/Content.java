package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * A content.
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
@Table(name = "content", uniqueConstraints = {
		@UniqueConstraint(name = "content_unique_name_idx", columnNames = { "name" }) })
public class Content implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Example: 25479
	@Column(name = "code")
	private Integer code;

	// Example: Jus d'ananas pour ananas appertisé au jus
	@Column(name = "name")
	@NotEmpty(message = "{error.content.RequiredName}")
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
	@Column(name = "sucres")
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

	@Column(name = "image_url")
	private String imageUrl;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name ="typeMeals")
	private List<String> typeMeals;

	private Boolean mixed;
}
