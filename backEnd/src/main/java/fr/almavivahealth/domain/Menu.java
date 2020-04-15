package fr.almavivahealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* @author christopher
* A menu.
*/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Integer weekNumber;
	
	@ManyToOne
	private Texture texture;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<TypeMeal> replacements;
	
	@ManyToOne
	private Diet diet;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Day> days;

}
