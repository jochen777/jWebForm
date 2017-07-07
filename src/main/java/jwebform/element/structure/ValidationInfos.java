package jwebform.element.structure;

import jwebform.env.Env;
import jwebform.validation.ValidationResult;

// information that are needed to get a validationResult from an element
public class ValidationInfos {
	private final String formId;
	private final Env env;
	
	public ValidationInfos(String formId, Env env) {
		this.formId = formId;
		this.env = env;
	}
	

	public String getFormId() {
		return formId;
	}

	public Env getEnv() {
		return env;
	}
	
}
