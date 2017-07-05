package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is not greater than the specified maximum.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class MaxLength implements Criterion {
	private int maxLength;

	MaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public ValidationResult validate(String value) {
		boolean isValid = value.length() <= maxLength;
		if (!isValid) {
			return ValidationResult.fail("jformchecker.max_len", maxLength);
		}
		return ValidationResult.ok();
	}

}
