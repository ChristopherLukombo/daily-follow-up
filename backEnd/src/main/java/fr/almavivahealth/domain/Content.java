package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class Content implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Texture texture;
	
    @NotNull
    @Column(nullable = false)
	private boolean salt;
	
    @NotNull
    @Column(nullable = false)
	private boolean sugar;

	public Content() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(final Texture texture) {
		this.texture = texture;
	}

	public boolean isSalt() {
		return salt;
	}

	public void setSalt(final boolean salt) {
		this.salt = salt;
	}

	public boolean isSugar() {
		return sugar;
	}

	public void setSugar(final boolean sugar) {
		this.sugar = sugar;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, salt, sugar, texture);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Content other = (Content) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && salt == other.salt
				&& sugar == other.sugar && Objects.equals(texture, other.texture);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Content [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (texture != null) {
			builder.append("texture=");
			builder.append(texture);
			builder.append(", ");
		}
		builder.append("salt=");
		builder.append(salt);
		builder.append(", sugar=");
		builder.append(sugar);
		builder.append("]");
		return builder.toString();
	}
}
