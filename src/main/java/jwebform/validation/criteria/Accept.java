package jwebform.validation.criteria;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is equal to one of the acceptable values.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Accept implements Criterion {
	private String[] acceptableValues;

	Accept(String... values) {
		this.acceptableValues = values;
	}

	protected boolean areEqual(String v1, String v2) {
		return v1.equals(v2);
	}

	@Override
	public ValidationResult validate(Validateable value) {
		boolean isValid = false;
		for (String v : acceptableValues) {
			if (areEqual(v, value.getValue()))
				isValid = true;
		}
		if (!isValid) {
			return ValidationResult.fail("jformchecker.allowed_values", (Object[]) acceptableValues);
		}
		return ValidationResult.ok();
	}

}
