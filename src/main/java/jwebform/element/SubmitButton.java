package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.validation.ValidationResult;

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
		return new ElementResult("submit", new SubmitRenderer(renderInfos.getTabIndex()));
	}

	@Override
	public int getTabIndexIncrement() {
		return 1;
	}

	public class SubmitRenderer implements HTMLProducer {
		
		private final int tabIndex;
		
		public SubmitRenderer(int tabIndex) {
			this.tabIndex = tabIndex;
		}

		@Override
		public String getHTML(ValidationResult vr) {
			return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + label + "\">";
		}
		
	}


}
