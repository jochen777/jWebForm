package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;

public class FormResult {

	private final Form form;
	private final Map<Element, ElementResult> elementResults;
	private final boolean formIsValid;
	
	public FormResult(Form form, Map<Element, ElementResult> elementResults, boolean formIsValid) {
		this.form = form;
		this.formIsValid = formIsValid;
		this.elementResults = elementResults;
	}
	 
	public boolean isOk() {
		return formIsValid;
	}

	public View getView() {
		return new View(form, elementResults);
	}


}
