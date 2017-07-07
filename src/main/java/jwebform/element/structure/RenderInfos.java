package jwebform.element.structure;

import jwebform.env.Env;

// information that are needed to render a form element completly
public class RenderInfos {
	private final String formId;
	private final Env env;
	// maybe: MessageSource, AdditionalAttributes
	
	public RenderInfos(String formId, Env env) {
		this.formId = formId;
		this.env = env;
	}
	
	public RenderInfos cloneWithNewTabIndexIncrease(int tabIndexIncrease) {
		return new RenderInfos(formId, env);
	}

	public String getFormId() {
		return formId;
	}

	public Env getEnv() {
		return env;
	}
	
}
