package jwebform.element.structure;

import jwebform.env.Request;
import jwebform.validation.ValidationResult;

// this form-element can be validated
public interface Validateable {

	public ValidationResult run(Request request);
	
	// TODO: getValidationResult()
	
	// TODO: Must take arguemnt ValidationResult
	public void overwriteValidationResult(ValidationResult vr);
	
	// TODO: Do we really need this?
	public String getValue();
	
}
