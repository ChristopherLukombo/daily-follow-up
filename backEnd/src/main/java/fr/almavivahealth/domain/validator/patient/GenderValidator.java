package fr.almavivahealth.domain.validator.patient;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class GenderValidator.
 *
 * @author christopher
 * @version 17
 */
public class GenderValidator implements ConstraintValidator<ValidGender, String> {

	private static final List<String> GENDERS = Arrays.asList("Homme", "Femme");

	/**
	 * Check the patient's gender is valid.
	 *
	 * @param value the value
	 * @param context the context
	 * @return true, if is valid
	 */
	@Override
	public boolean isValid(final String gender, final ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(gender) && GENDERS.contains(StringUtils.capitalize(gender));
	}

}
