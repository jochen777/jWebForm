package jwebform.element.structure;

import jwebform.validation.ValidationResult;

public interface Element {

	public String getHtml(int tabIndex, ValidationResult overridenValidationResult);
	
}
