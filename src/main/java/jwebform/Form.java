package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.DoubleTakenNameException;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// Represents a form
public class Form {

  private final List<Element> elements;
  private final String id;
  private final List<FormValidator> formValidators;

  public Form(String id, List<Element> elements) {
    this(id, elements, new ArrayList<>());
  }

  public Form(String id, List<Element> elements, List<FormValidator> formValidators) {
    this.elements = elements;
    this.id = id;
    this.formValidators = formValidators;
  }

  public Form(String id, Element... elements) {
    this(id, new ArrayList<>(), elements);
  }

  
  public Form(String id, List<FormValidator> formValidators, Element... elements) {
    this(id, Arrays.asList(elements), formValidators);
  }

  public FormResult run(Env env) {
    return run(env, false);
  }
  
  public FormResult run(Env env, boolean debug) {
    // validate form
    Map<Element, ElementResult> elementResults = processElements(env.getEnvWithSumitInfo(id));
    if (debug) {
      checkDoubleElements(elementResults);
    }
    Map<Element, ValidationResult> overridenValidationResults = runFormValidations(elementResults);
    Map<Element, ElementResult> correctedElementResults =
        correctElementResults(elementResults, overridenValidationResults);
    boolean formIsValid = checkAllValidationResults(correctedElementResults);

    return new FormResult(this.getId(), correctedElementResults, formIsValid);
  }

  private void checkDoubleElements(Map<Element, ElementResult> results) {
    Set<String> availElements = new HashSet<>();
    results.forEach((k,v) -> {
      if (!availElements.add(v.getStaticElementInfo().getName())) {
        // TODO: Choose own Exception here!
        throw new DoubleTakenNameException(v.getStaticElementInfo().getName());
      }
    });
  }

  private Map<Element, ElementResult> processElements(EnvWithSubmitInfo envWithSubmitInfo) {
    // check each element
    Map<Element, ElementResult> elementResults = new LinkedHashMap<>();
    for (Element element : elements) {
      ElementResult result = element.apply(envWithSubmitInfo);
      elementResults.put(element, result);
    }
    return elementResults;
  }

  private Map<Element, ValidationResult> runFormValidations(
      Map<Element, ElementResult> elementResults) {
    // run the form-validators
    Map<Element, ValidationResult> overridenValidationResults = new LinkedHashMap<>();
    for (FormValidator formValidator : formValidators) {
      overridenValidationResults.putAll(formValidator.validate(elementResults));
    }
    return overridenValidationResults;
  }

  private Map<Element, ElementResult> correctElementResults(
      Map<Element, ElementResult> elementResults,
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

}
