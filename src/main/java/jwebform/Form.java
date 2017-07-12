package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.Theme;
import jwebform.env.Env;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// Represents a form
public class Form {

	private final List<Element> elements = new ArrayList<>();
	private final String id;
	private final List<FormValidator> formValidators = new ArrayList<>();

	protected String method = "POST";
	
	public Form(String id, List<Element> elements, List<FormValidator> formValidators) {
		this.elements.addAll(elements);
		this.id = id;
		this.formValidators.addAll(formValidators);
	}

	   public Form(String id, List<FormValidator> formValidators, Element ... elements) {
	        this.elements.addAll(Arrays.asList(elements));
	        this.id = id;
	        this.formValidators.addAll(formValidators);
	    }

	
	public FormResult run(Env env, Theme theme) {
		// validate form
		Map<Element, ElementResult> elementResults = processElements(env, theme);
		Map<Element, ValidationResult> overridenValidationResults = runFormValidations(elementResults);
		Map<Element, ElementResult> correctedElementResults = correctElementResults(elementResults, overridenValidationResults);
		boolean formIsValid = checkAllValidationResults(correctedElementResults);
		
		return new FormResult(this, correctedElementResults, formIsValid);
	}



	private boolean checkAllValidationResults(Map<Element, ElementResult> correctedElementResults) {
		boolean formIsValid = true;
		for (Map.Entry<Element, ElementResult> entry : correctedElementResults.entrySet()) {
			if (entry.getValue().getValidationResult() != ValidationResult.ok()) {
				formIsValid = false;
				break;
			}
		}
		return formIsValid;
	}


	private Map<Element, ElementResult> correctElementResults(Map<Element, ElementResult> elementResults,
			Map<Element, ValidationResult> overridenValidationResults) {
		overridenValidationResults.forEach(
				(element, overridenValidationResult) -> {
					ElementResult re = elementResults.get(element);
					elementResults.put(element, new ElementResult(re.getName(), re.getHtmlProducer(), 
							overridenValidationResult, re.getValue(), re.getTabIndexIncrement(), re.getSource(), re.getRenderKey()));
				}
				);
		return elementResults;
	}


	private Map<Element, ValidationResult> runFormValidations(Map<Element, ElementResult> elementResults) {
		// run the form-validators
		Map<Element, ValidationResult> overridenValidationResults = new LinkedHashMap<>();
		for (FormValidator formValidator : formValidators) {
			overridenValidationResults.putAll(formValidator.validate(elementResults));
		}
		return overridenValidationResults;
	}


	private Map<Element, ElementResult> processElements(Env env, Theme theme) {
		// check each element
		Map<Element, ElementResult> elementResults = new LinkedHashMap<>();
		for (Element element : elements) {
			PrepareInfos renderInfos = new PrepareInfos(id, env, theme);
			ElementResult result = element.prepare(renderInfos);
			elementResults.put(element, result);
		}
		return elementResults;
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
