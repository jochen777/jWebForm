package jwebform.element.structure;

import jwebform.validation.ValidationResult;

public interface Element {

	// RFE: Introduce "RenderHints" Object with tabIndex, MessageSource, additionalAttributes
	public String getHtml(int tabIndex, ValidationResult overridenValidationResult);
	
}
