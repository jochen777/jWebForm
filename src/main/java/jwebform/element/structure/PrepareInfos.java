package jwebform.element.structure;

import jwebform.env.Env;

// information that are needed to prepare a form element completly
public class PrepareInfos {
	private final String formId;
	private final Env env;
	private final Theme theme;
	// maybe: MessageSource, AdditionalAttributes
	
	public PrepareInfos(String formId, Env env, Theme theme) {
		this.formId = formId;
		this.env = env;
		this.theme = theme;
	}
	
	public PrepareInfos cloneWithNewTabIndexIncrease(int tabIndexIncrease) {
		return new PrepareInfos(formId, env, theme);
	}

	public String getFormId() {
		return formId;
	}

	public Env getEnv() {
		return env;
	}

	public Theme getTheme() {
		return theme;
	}
	
}
