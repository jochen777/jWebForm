package jwebform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.Validateable;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// Represents a form
public class Form {

	List<Element> elements = new ArrayList<>();
	String id = "id";
	List<FormValidator> formValidators = new ArrayList<>();
	// RFE: This could be the only store for elements. we don't need the list.
	private Map<Element, ValidationResult> overridenValidationResults = new LinkedHashMap<>();

	protected String method = "POST";
	
	public FormResult run() {
		// validate form
		return new FormResult(this, checkIfValid(), overridenValidationResults);
	}



	private boolean checkIfValid() {
		// check each element
		boolean completeResult = true;
		for (Element element : elements) {
			if (element instanceof Validateable) {
				if (!((Validateable) element).getValidationResult().isValid) {
					completeResult = false;
					break;
				}
			}
		}
		// run the form-validators
		for (FormValidator formValidator : formValidators) {
			completeResult = formValidator.validate(this)?completeResult:false;
		}
		
		return completeResult;
	}


	
	public void addElement(Element element) {
		elements.add(element);
	}
	
	public void overrideValidationResult(Element element, ValidationResult validationResult) {
		overridenValidationResults.put(element, validationResult);
	}

	List<Element> getElements() {
		return elements;
	}

	
	public void addFormValidator(FormValidator formValidator) {
		formValidators.add(formValidator);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public boolean isHtml5Validate() {
		return true; // TODO: Make this configurable
	}


}
