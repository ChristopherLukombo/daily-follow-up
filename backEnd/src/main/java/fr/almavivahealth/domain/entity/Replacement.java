package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* @author christopher
* A replacement.
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

	@OneToMany
	private List<Content> entries;

	@OneToMany
	private List<Content> dishes;

	@OneToMany
	private List<Content> desserts;

	@OneToMany
	private List<Content> starchyFoods;

	@OneToMany
	private List<Content> vegetables;

	@OneToMany
	private List<Content> dairyProducts;
}
