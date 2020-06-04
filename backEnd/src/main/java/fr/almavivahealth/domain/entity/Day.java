package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
* @author christopher
* A day.
*/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Day implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	private List<MomentDay> momentDays;
}
