package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* @author christopher
* A user.
*/
@Entity
@Table(name="\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO : Changer la regex pour le pseudo.
	@NotNull
    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String pseudo;

    @JsonIgnore
    @NotNull
    @Size(max = 60)
    @Column(name = "password_hash", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "createDate")
    @CreatedDate
    private LocalDate createDate;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private boolean status = true;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    private Boolean hasChangedPassword;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;
}
