package jwebform.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator, that checks a form-element
 * 
 * @author jochen
 *
 */
public class Validator {

	private final List<Criterion> criteria = new ArrayList<>();

	public Validator(Criterion... inputCriterium) {
		for (Criterion cirterion : inputCriterium) {
			criteria.add(cirterion);
		}
	}

	public ValidationResult validate(String value) { // RFE: Better just
														// object??
		ValidationResult vr = ValidationResult.ok();
		vr = allCriteriaSatisfied(value);
		return vr;
	}

	// RFE: Maybe return here an array? So we can have more than one
	// error-message per field.
	private ValidationResult allCriteriaSatisfied(String value) {
		for (Criterion criterion : criteria) {
			ValidationResult vr = criterion.validate(value);
			if (!vr.isValid()) {
				return vr;
			}
		}

		return ValidationResult.ok();
	}

}
