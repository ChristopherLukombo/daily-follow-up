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
 * A DTO for the Content entity.
 *
 * @author christopher
 * @version 17
 */
@AllArgsConstructor
@Builder
public class ContentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer code;

	@NotEmpty(message = "{error.content.RequiredName}")
	private String name;

	private String groupName;

	private String subGroupName;

	private String subSubGroupName;

	private Double calories;

	private Double protein;

	private Double carbohydrate;

	private Double lipids;

	private Double sugars;

	private Double foodFibres;

	private Double agSaturates;

	private Double salt;

	private String imageUrl;

	private List<String> typeMeals;

	private Boolean mixed;

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

	public Double getCalories() {
		return calories;
	}

	public void setCalories(final Double calories) {
		this.calories = calories;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(final Double protein) {
		this.protein = protein;
	}

	public Double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(final Double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public Double getLipids() {
		return lipids;
	}

	public void setLipids(final Double lipids) {
		this.lipids = lipids;
	}

	public Double getSugars() {
		return sugars;
	}

	public void setSugars(final Double sugars) {
		this.sugars = sugars;
	}

	public Double getFoodFibres() {
		return foodFibres;
	}

	public void setFoodFibres(final Double foodFibres) {
		this.foodFibres = foodFibres;
	}

	public Double getAgSaturates() {
		return agSaturates;
	}

	public void setAgSaturates(final Double agSaturates) {
		this.agSaturates = agSaturates;
	}

	public Double getSalt() {
		return salt;
	}

	public void setSalt(final Double salt) {
		this.salt = salt;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getTypeMeals() {
		return typeMeals;
	}

	public void setTypeMeals(final List<String> typeMeals) {
		this.typeMeals = typeMeals;
	}

	public Boolean getMixed() {
		return mixed;
	}

	public void setMixed(final Boolean mixed) {
		this.mixed = mixed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agSaturates, calories, carbohydrate, mixed, code, foodFibres, groupName, id,
				imageUrl, lipids, name, protein, salt, subGroupName, subSubGroupName, sugars, typeMeals);
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
				&& Objects.equals(carbohydrate, other.carbohydrate)
				&& Objects.equals(mixed, other.mixed) && Objects.equals(code, other.code)
				&& Objects.equals(foodFibres, other.foodFibres) && Objects.equals(groupName, other.groupName)
				&& Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(lipids, other.lipids) && Objects.equals(name, other.name)
				&& Objects.equals(protein, other.protein) && Objects.equals(salt, other.salt)
				&& Objects.equals(subGroupName, other.subGroupName)
				&& Objects.equals(subSubGroupName, other.subSubGroupName) && Objects.equals(sugars, other.sugars)
				&& Objects.equals(typeMeals, other.typeMeals);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
