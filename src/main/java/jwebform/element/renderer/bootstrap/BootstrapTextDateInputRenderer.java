package jwebform.element.renderer.bootstrap;

import java.util.List;

import jwebform.element.TextDateInput;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.validation.ValidationResult;

public class BootstrapTextDateInputRenderer implements HTMLProducer{

	
	@Override
	public String getHTML(Element inputSource, String formId, Object value, int tabIndex, ValidationResult vr, List<ElementResult> childs) {
		TextDateInput source = (TextDateInput)inputSource; 
		 String errorMessage = "";
	      if (vr != ValidationResult.undefined() && !vr.isValid) {
	          errorMessage = "Problem: " + vr.getMessage() + "<br>";
	      }
	      BootstrapTextInputRenderer htmlProducer = new BootstrapTextInputRenderer();
	      String html = source.getDecoration().getLabel() + "<br/>" 
	              + 
	              errorMessage + htmlProducer.getHTML(source.getDay(), formId, childs.get(0).getValue(), tabIndex, childs.get(0).getValidationResult(), null) +
	              htmlProducer.getHTML(source.getMonth(), formId, childs.get(1).getValue(), tabIndex+1, childs.get(1).getValidationResult(), null) +
	              htmlProducer.getHTML(source.getYear(), formId, childs.get(2).getValue(), tabIndex+2, childs.get(2).getValidationResult(), null) 
	              + "<br>" + source.getDecoration().getHelptext();
	      return html;
	}
}
