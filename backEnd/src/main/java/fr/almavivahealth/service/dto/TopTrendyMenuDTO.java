package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the TopTrendyMenu projection.
 *
 * @author christopher
 * @version 17
 */
@AllArgsConstructor
@Builder
public class TopTrendyMenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long nb;

	private String texture;

	private String diets;

	public TopTrendyMenuDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getNb() {
		return nb;
	}

	public void setNb(final Long nb) {
		this.nb = nb;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(final String texture) {
		this.texture = texture;
	}

	public String getDiets() {
		return diets;
	}

	public void setDiets(final String diets) {
		this.diets = diets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diets, nb, texture);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TopTrendyMenuDTO other = (TopTrendyMenuDTO) obj;
		return Objects.equals(diets, other.diets) && Objects.equals(nb, other.nb)
				&& Objects.equals(texture, other.texture);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
