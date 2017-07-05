package jwebform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.env.Env;
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
	
	
	public FormResult run(Env env) {
		// validate form
		return new FormResult(this, checkIfValid(env), env);
	}



	private FormValidationResult checkIfValid(Env env) {
		// check each element
		boolean completeResult = true;
		int tabIndex = 1;
		Map<Element, ElementResult> elementResults = new LinkedHashMap<>();
		for (Element element : elements) {
			RenderInfos renderInfos = new RenderInfos(id, tabIndex, env, ValidationResult.undefined());
			ElementResult result = element.getHtml(renderInfos);
			elementResults.put(element, result);
			if (!result.getValidationResult().isValid) {
				completeResult = false;
			}
		}
		/*/
		for (Element element : elements) {
			if (element instanceof Validateable) {
				if (!((Validateable) element).getValidationResult().isValid) {
					completeResult = false;
					break;
				}
			}
		}
		*/
		// run the form-validators
		Map<Element, ValidationResult> overridenValidationResults = new LinkedHashMap<>();

		for (FormValidator formValidator : formValidators) {
			overridenValidationResults.putAll(formValidator.validate(elementResults));
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
