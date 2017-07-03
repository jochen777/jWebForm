package jwebform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.Validateable;
import jwebform.validation.FormValidationResult;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// Represents a form
public class Form {

	final List<Element> elements = new ArrayList<>();
	final String id;
	final List<FormValidator> formValidators = new ArrayList<>();

	protected String method = "POST";
	
	public Form(String id, List<Element> elements, List<FormValidator> formValidators) {
		this.elements.addAll(elements);
		this.id = id;
		this.formValidators.addAll(formValidators);
	}
	
	
	public FormResult run() {
		// validate form
		return new FormResult(this, checkIfValid());
	}



	private FormValidationResult checkIfValid() {
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
		Map<Element, ValidationResult> overridenValidationResults = new LinkedHashMap<>();

		for (FormValidator formValidator : formValidators) {
			overridenValidationResults.putAll(formValidator.validate(elements));
		}
		
		FormValidationResult formValidationResult = new FormValidationResult(completeResult, overridenValidationResults);
		return formValidationResult;
	}


	
	List<Element> getElements() {
		return elements;
	}

	
	public String getId() {
		return id;
	}

	public String getMethod() {
		return method;
	}

	public boolean isHtml5Validate() {
		return true; // TODO: Make this configurable
	}


}
