package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.validation.ValidationResult;

public class SubmitButton implements Element {

	private final String label;
	
	public SubmitButton(String label) {
		this.label = label;
	}

	
	public String getLabel() {
		return label;
	}

	@Override
	public ElementResult run(RenderInfos renderInfos) {
		return new ElementResult("submit", new SubmitRenderer(), ValidationResult.ok(), "", 1);
	}

	public class SubmitRenderer implements HTMLProducer {
		
		@Override
		public String getHTML(ValidationResult vr, int tabIndex) {
			return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + label + "\">";
		}
		
	}


}
