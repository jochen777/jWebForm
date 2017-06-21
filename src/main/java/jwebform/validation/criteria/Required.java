package jwebform.validation.criteria;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if value starts with the given string.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Required implements Criterion {

	Required() {
	}

	@Override
	public ValidationResult validate(Validateable value) {
		if (value.getValue() == null || "".equals(value.getValue().trim())) {
			return ValidationResult.fail("jformchecker.required");
		}
		return ValidationResult.ok();
	}

}
