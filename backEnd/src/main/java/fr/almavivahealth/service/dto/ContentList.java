package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * ContentList for managing contents.
 */
@AllArgsConstructor
@Builder
public class ContentList implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{error.content.NotEmpty}")
	private List<ContentDTO> contents;

	public ContentList() {
		// Empty constructor needed for Jackson.
	}

	public List<ContentDTO> getContents() {
		return contents;
	}

	public void setContents(final List<ContentDTO> contents) {
		this.contents = contents;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contents);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContentList other = (ContentList) obj;
		return Objects.equals(contents, other.contents);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
