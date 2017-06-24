package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.validation.ValidationResult;

public class FormResult {

	private Form form;
	private boolean valid;
	Map<Element, ValidationResult> overridenValidationResults;
	
	public FormResult(Form form, boolean valid,  Map<Element, ValidationResult> overridenValidationResults) {
		this.form = form;
		this.valid = valid;
		this.overridenValidationResults = overridenValidationResults;
	}
	 
	public boolean isOk() {
		return valid;
	}

	public View getView() {
		return new View(form, overridenValidationResults);
	}

}
