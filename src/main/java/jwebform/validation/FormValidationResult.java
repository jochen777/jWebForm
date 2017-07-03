package jwebform.validation;

import java.util.Map;

import jwebform.element.structure.Element;

// represents the validationresults for a complete form
public class FormValidationResult {
	
	private final boolean formIsValid;
	private final Map<Element, ValidationResult> overridenValidationResults;

	
	public FormValidationResult(boolean formIsValid, Map<Element, ValidationResult> overridenValidationResults) {
		this.formIsValid = formIsValid;
		this.overridenValidationResults = overridenValidationResults;
	}


	public boolean isFormIsValid() {
		// TODO: Check the overridenValidationResults - maybe they contain an error!
		return formIsValid;
	}


	public Map<Element, ValidationResult> getOverridenValidationResults() {
		return overridenValidationResults;
	}

}
