package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* A replacement.
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
public class Replacement implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
}
