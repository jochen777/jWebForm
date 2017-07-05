package jwebform.validation;

import jwebform.element.structure.Validateable;

/**
 * A criterion that checks a formchecker element
 * 
 */
@FunctionalInterface
public interface Criterion {
	/**
	 * Tests whether the specified value satisfies this criterion.
	 * 
	 * @param value
	 *            to be tested against this criterion.
	 * @return a ValidationResult which holds true or false for validaton result
	 *         and a potential errormsg
	 */
	public ValidationResult validate(String value);

}
