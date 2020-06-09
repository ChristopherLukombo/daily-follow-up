package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
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
* @author christopher
* A diet.
*/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Diet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ElementCollection
	@CollectionTable(name ="elementsToCheck")
	private List<String> elementsToCheck;
}
