package jwebform.element;

import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
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
	public ElementResult prepare(PrepareInfos renderInfos) {
		return new ElementResult("submit", new SubmitRenderer(), ValidationResult.ok(), "", 1, this, "jwebform.element.SubmitButton");
	}

	public class SubmitRenderer implements HTMLProducer {
		
		@Override
		public String getHTML(Element inputSource, String formId, Object value, int tabIndex, ValidationResult vr, List<ElementResult> childs){
			return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + label + "\">";
		}
		
	}


}
