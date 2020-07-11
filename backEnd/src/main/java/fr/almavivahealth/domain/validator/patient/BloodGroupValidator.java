package fr.almavivahealth.domain.validator.patient;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class BloodGroupValidator.
 *
 * @author christopher
 * @version 16
 */
public class BloodGroupValidator implements ConstraintValidator<ValidBloodGroup, String> {

	private static final List<String> BLOOD_GROUPS = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

	/**
	 * Checks the patient's blood group is valid.
	 *
	 * @param value the value
	 * @param context the context
	 * @return true, if is valid
	 */
	@Override
	public boolean isValid(final String bloodGroup, final ConstraintValidatorContext context) {
		if (StringUtils.isBlank(bloodGroup)) {
			return true;
		}
		return BLOOD_GROUPS.contains(bloodGroup.toUpperCase());
	}

}
