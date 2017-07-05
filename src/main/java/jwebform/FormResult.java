package jwebform;

import jwebform.env.Env;
import jwebform.validation.FormValidationResult;

public class FormResult {

	private final Form form;
	private final FormValidationResult formValidationResult;
	private final Env env;
	
	public FormResult(Form form, FormValidationResult formValidationResult, Env env) {
		this.form = form;
		this.formValidationResult = formValidationResult;
		this.env = env;
	}
	 
	public boolean isOk() {
		return formValidationResult.isFormIsValid();
	}

	public View getView() {
		return new View(form, formValidationResult.getOverridenValidationResults(), env);
	}

	public Env getEnv() {
		return env;
	}

}
