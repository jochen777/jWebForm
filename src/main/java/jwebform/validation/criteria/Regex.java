package jwebform.validation.criteria;

import java.util.regex.Pattern;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if a string matches a regular expression.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Regex implements Criterion {
	private Pattern pattern;
	private String errorMsg = "jformchecker.regexp";

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	Regex(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	public ValidationResult validate(Validateable value) {
		boolean isValid = pattern.matcher(value.getValue()).find();
		if (!isValid) {
			return ValidationResult.fail(errorMsg);
		}
		return ValidationResult.ok();
	}

}