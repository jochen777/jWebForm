package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;

public class SubmitButton implements TabIndexAwareElement {

	private final String label;
	
	public SubmitButton(String label) {
		this.label = label;
	}

	
	public String getLabel() {
		return label;
	}

	@Override
	public ElementResult run(RenderInfos renderInfos) {
		return new ElementResult("submit", "<input tabindex=\"" + renderInfos.getTabIndex() + "\" type=\"submit\" value=\"" + label + "\">");
	}

	@Override
	public int getTabIndexIncrement() {
		return 1;
	}


}
