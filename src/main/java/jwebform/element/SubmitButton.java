package jwebform.element;

import jwebform.element.structure.TabIndexAwareElement;

public class SubmitButton implements TabIndexAwareElement {

	String label = "Submit";

	public SubmitButton(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String getHtml(int tabIndex) {
		return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + label + "\">";
	}

	@Override
	public int getTabIndexIncrement() {
		return 1;
	}

}
