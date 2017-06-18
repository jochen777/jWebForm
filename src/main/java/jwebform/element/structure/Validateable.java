package jwebform.element.structure;

import jwebform.env.Request;

// this form-element can be validated
public interface Validateable {

	public void run(Request request);
	
	// TODO: getValidationResult()
	
	// TODO: Must take arguemnt ValidationResult
	public void overwriteValidationResult();
	
	// TODO: Do we really need this?
	public String getValue();
	
}
