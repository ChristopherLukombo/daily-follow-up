package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Content entity.
 */
@AllArgsConstructor
@Builder
public class ContentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer code;

	private String name;

    private String groupName;

    private String subGroupName;

    private String subSubGroupName;

    private String calories;

    private String protein;

    private String carbohydrate;

    private String lipids;

    private String sugars;

    private String foodFibres;

    private String agSaturates;

    private String salt;

	private String imageUrl;

	public ContentDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(final Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(final String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getSubSubGroupName() {
		return subSubGroupName;
	}

	public void setSubSubGroupName(final String subSubGroupName) {
		this.subSubGroupName = subSubGroupName;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(final String calories) {
		this.calories = calories;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(final String protein) {
		this.protein = protein;
	}

	public String getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(final String carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public String getLipids() {
		return lipids;
	}

	public void setLipids(final String lipids) {
		this.lipids = lipids;
	}

	public String getSugars() {
		return sugars;
	}

	public void setSugars(final String sugars) {
		this.sugars = sugars;
	}

	public String getFoodFibres() {
		return foodFibres;
	}

	public void setFoodFibres(final String foodFibres) {
		this.foodFibres = foodFibres;
	}

	public String getAgSaturates() {
		return agSaturates;
	}

	public void setAgSaturates(final String agSaturates) {
		this.agSaturates = agSaturates;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agSaturates, calories, carbohydrate, code, foodFibres, groupName, id, imageUrl, lipids,
				name, protein, salt, subGroupName, subSubGroupName, sugars);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContentDTO other = (ContentDTO) obj;
		return Objects.equals(agSaturates, other.agSaturates) && Objects.equals(calories, other.calories)
				&& Objects.equals(carbohydrate, other.carbohydrate) && Objects.equals(code, other.code)
				&& Objects.equals(foodFibres, other.foodFibres) && Objects.equals(groupName, other.groupName)
				&& Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(lipids, other.lipids) && Objects.equals(name, other.name)
				&& Objects.equals(protein, other.protein) && Objects.equals(salt, other.salt)
				&& Objects.equals(subGroupName, other.subGroupName)
				&& Objects.equals(subSubGroupName, other.subSubGroupName) && Objects.equals(sugars, other.sugars);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
