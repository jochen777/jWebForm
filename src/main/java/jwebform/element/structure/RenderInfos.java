package jwebform.element.structure;

import jwebform.env.Env;
import jwebform.validation.ValidationResult;

// information that are needed to render a form element completly
public class RenderInfos {
	private final String formId;
	private final int tabIndex;
	private final Env env;
	// maybe: MessageSource, AdditionalAttributes
	
	public RenderInfos(String formId, int tabIndex, Env env) {
		this.formId = formId;
		this.tabIndex = tabIndex;
		this.env = env;
	}
	
	public RenderInfos cloneWithNewTabIndexIncrease(int tabIndexIncrease) {
		return new RenderInfos(formId, tabIndex + tabIndexIncrease, env);
	}

	public String getFormId() {
		return formId;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	
	public Env getEnv() {
		return env;
	}
	
}
