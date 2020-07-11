package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Comment entity.
 *
 * @author christopher
 * @version 17
 */
@AllArgsConstructor
@Builder
public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(min = 2, max = 50, message = "{error.comment.content}")
	private String content;

	@NotNull
	private String pseudo;

	@NotNull
	private LocalDateTime lastModification;

	public CommentDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(final String pseudo) {
		this.pseudo = pseudo;
	}

	public LocalDateTime getLastModification() {
		return lastModification;
	}

	public void setLastModification(final LocalDateTime lastModification) {
		this.lastModification = lastModification;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, id, lastModification, pseudo);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CommentDTO other = (CommentDTO) obj;
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(lastModification, other.lastModification) && Objects.equals(pseudo, other.pseudo);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
