package jwebform.element.renderer.bootstrap;

import java.util.List;

import jwebform.element.SubmitButton;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.validation.ValidationResult;

public class BootstrapSubmitButtonRenderer implements HTMLProducer{

	
	@Override
	public String getHTML(Element inputSource, String formId, Object value, int tabIndex, ValidationResult vr, List<ElementResult> childs) {
		SubmitButton source = (SubmitButton)inputSource;
		return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + source.getLabel() + "\">";
	}
}
