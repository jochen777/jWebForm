package jwebform.element.structure;

import jwebform.validation.ValidationResult;

// this form-element can be validated
public interface Validateable {

	public ValidationResult getValidationResult();
	
	
	// TODO: Do we really need this?
	public String getValue();
	
}
