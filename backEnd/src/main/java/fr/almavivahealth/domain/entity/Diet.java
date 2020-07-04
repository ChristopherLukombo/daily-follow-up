package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author christopher A diet.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@Table(name = "diet", uniqueConstraints = {
		@UniqueConstraint(name = "diet_unique_name_idx", columnNames = { "name" }) })
public class Diet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@ElementCollection
	@MapKeyColumn(name = "elements_to_check_key", nullable = true)
	@Column(name = "elements_to_check", nullable = true)
	@CollectionTable(name = "elementsToCheck")
	private Map<String, Integer> elementsToCheck;
}
