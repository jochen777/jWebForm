package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.validation.ValidationResult;

public class SimpleElement implements Element{

	
	@Override
	public String getHtml(int tabIndex, ValidationResult overrideValidationResult) {
		return "simple\n";
	}

	

}
