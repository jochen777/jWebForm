package jwebform.validation.criteria;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that the length of the value is equal to the given length.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class ExactLength implements Criterion {
	private int length;

	ExactLength(int length) {
		this.length = length;
	}

	@Override
	public ValidationResult validate(Validateable value) {
		boolean isValid = value.getValue().length() == length;
		if (!isValid) {
			return ValidationResult.fail("jformchecker.exact_lenght", Integer.valueOf(length));
		}
		return ValidationResult.ok();
	}

}