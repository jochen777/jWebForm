package jwebform.validation.criteria;

import java.util.regex.Pattern;

import jwebform.element.structure.Validateable;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if a string matches a regular expression in a case insensitive way.
 * RFE: Avoid doublication with Regex.java
 * 
 * Based on work of armandino (at) gmail.com
 */
public class CaseInsensitiveRegex implements Criterion {

	private Pattern pattern;
	private String errorMsg = "jformchecker.regexp";

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	CaseInsensitiveRegex(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public ValidationResult validate(String value) {
		boolean isValid = pattern.matcher(value).find();
		if (!isValid) {
			return ValidationResult.fail(errorMsg);
		}
		return ValidationResult.ok();
	}
}
