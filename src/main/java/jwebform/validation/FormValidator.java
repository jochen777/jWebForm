package jwebform.validation;

import jwebform.Form;

// Validates a complete Form
@FunctionalInterface
public interface FormValidator {
	/**
	 * checks a complete form. 
	 * If something is invalid, return false. If everything is okay, return true
	 * @param form
	 * @return
	 */
	public boolean validate(Form form);
}
