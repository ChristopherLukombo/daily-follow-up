package fr.almavivahealth.domain.validator.patient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BloodGroupValidator.class)
public @interface ValidBloodGroup {

	String message() default "{error.patient.bloodGroup_not_valid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
