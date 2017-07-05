package jwebform.validation.criteria;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is not less than the specified minimum.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class MinLength implements Criterion {
	private int minLength;

	MinLength(int minLength) {
		this.minLength = minLength;
	}

	@Override
	public ValidationResult validate(String value) {
		boolean isValid = value.length() >= minLength;
		if (!isValid) {
			return ValidationResult.fail("jformchecker.min_len", minLength);
		}
		return ValidationResult.ok();
	}

}
