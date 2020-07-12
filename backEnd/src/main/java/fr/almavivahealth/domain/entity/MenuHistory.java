package fr.almavivahealth.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import fr.almavivahealth.domain.enums.Action;

/**
*
* A MenuHistory.
*
 * @author christopher
 * @version 17
*/
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MenuHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @CreatedBy
    private String modifiedBy;

    @CreatedDate
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private Action action;

    public MenuHistory() {
    	// Empty constructor needed for Hibernate.
	}

    public MenuHistory(final Menu menu, final Action action) {
        this.menu = menu;
        this.action = action;
    }

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(final Menu menu) {
		this.menu = menu;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(final Action action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, id, menu, modifiedBy, modifiedDate);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MenuHistory other = (MenuHistory) obj;
		return action == other.action && Objects.equals(id, other.id) && Objects.equals(menu, other.menu)
				&& Objects.equals(modifiedBy, other.modifiedBy) && Objects.equals(modifiedDate, other.modifiedDate);
	}
}
