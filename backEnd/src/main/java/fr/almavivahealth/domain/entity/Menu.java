package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fr.almavivahealth.domain.listener.MenuEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author christopher A menu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EntityListeners({ AuditingEntityListener.class, MenuEntityListener.class })
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotBlank
	private String texture;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Replacement replacement;

	private String diet;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	private List<Week> weeks;

	@LastModifiedBy
	private String lastModifiedBy;

	@LastModifiedDate
	private LocalDateTime lastModificationDateBy;
}
