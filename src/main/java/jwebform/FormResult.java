package jwebform;

import jwebform.validation.FormValidationResult;

public class FormResult {

	private final Form form;
	private final FormValidationResult formValidationResult;
	
	public FormResult(Form form, FormValidationResult formValidationResult) {
		this.form = form;
		this.formValidationResult = formValidationResult;
	}
	 
	public boolean isOk() {
		return formValidationResult.isFormIsValid();
	}

	public View getView() {
		return new View(form, formValidationResult.getOverridenValidationResults());
	}

}
