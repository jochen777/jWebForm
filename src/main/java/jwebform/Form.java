package jwebform;

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

	private final List<Element> elements;
	private final String id;
	private final List<FormValidator> formValidators;

	protected String method = "POST";

	public Form(String id, List<Element> elements, List<FormValidator> formValidators) {
		this.elements = elements;
		this.id = id;
		this.formValidators = formValidators;
	}

	public Form(String id, List<FormValidator> formValidators, Element... elements) {
		this(id, Arrays.asList(elements), formValidators);
	}

	public FormResult run(Env env) {
		// validate form
		Map<Element, ElementResult> elementResults = processElements(env);
		Map<Element, ValidationResult> overridenValidationResults = runFormValidations(elementResults);
		Map<Element, ElementResult> correctedElementResults = correctElementResults(elementResults,
				overridenValidationResults);
		boolean formIsValid = checkAllValidationResults(correctedElementResults);

		return new FormResult(this, correctedElementResults, formIsValid);
	}

	private Map<Element, ElementResult> processElements(Env env) {
		// check each element
		Map<Element, ElementResult> elementResults = new LinkedHashMap<>();
		PrepareInfos renderInfos = new PrepareInfos(id, env);
		for (Element element : elements) {
			ElementResult result = element.prepare(renderInfos);
			elementResults.put(element, result);
		}
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

	private Map<Element, ElementResult> correctElementResults(Map<Element, ElementResult> elementResults,
			Map<Element, ValidationResult> overridenValidationResults) {
		overridenValidationResults.forEach((element, overridenValidationResult) -> {
			ElementResult re = elementResults.get(element);
			elementResults.put(element, re.cloneWithNewValidationResult(overridenValidationResult));
		});
		return elementResults;
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
