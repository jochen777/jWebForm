package jwebform.element.structure;

// this form-element can be validated
public interface Validateable {

	// TODO: Must take arguemnt ValidationResult
	public void overwriteValidationResult();
	
	// TODO: Do we really need this?
	public String getValue();
	
}
