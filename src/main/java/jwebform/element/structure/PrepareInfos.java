package jwebform.element.structure;

import jwebform.env.Env;

// information that are needed to prepare a form element completly
public class PrepareInfos {
	private final String formId;
	private final Env env;
	// maybe: MessageSource, AdditionalAttributes
	
	public PrepareInfos(String formId, Env env) {
		this.formId = formId;
		this.env = env;
	}
	
	public PrepareInfos cloneWithNewTabIndexIncrease(int tabIndexIncrease) {
		return new PrepareInfos(formId, env);
	}

	public String getFormId() {
		return formId;
	}

	public Env getEnv() {
		return env;
	}
	
}
