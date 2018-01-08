package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processors.Processor;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// Represents a form
public final class Form {

  // RFE: maybe keep List<Element> for performance reasons, if we don't have any
  // behaviours/validation
  private final List<ElementContainer> elements;
  private final String id;
  private final List<FormValidator> formValidators;
  private final Processor processor;

  // Constructors

  // full
  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
    this.elements = elements;
    this.id = id;
    this.formValidators = formValidators;
    this.processor = new Processor();
  }

  public Form(String id, List<ElementContainer> elements) {
    this(id, elements, new ArrayList<>());
  }


  public Form(String id, ElementContainer... elements) {
    this(id, new ArrayList<>(), elements);
  }

  public Form(String id, Element... elements) {
    this(id, packElementsInContainer(elements), new ArrayList<>());
  }


  public Form(String id, List<FormValidator> formValidators, ElementContainer... elements) {
    this(id, Arrays.asList(elements), formValidators);
  }

  public Form(String id, List<FormValidator> formValidators, Element... elements) {
    this(id, packElementsInContainer(elements), formValidators);
  }
  // End constructors

  public final FormResult run(Env env) {
    // validate form
    Map<ElementContainer, ElementResult> elementResults = processor.processElements(env.getEnvWithSumitInfo(id, this), elements, id);
    elementResults = processor.runPostProcessors(elementResults);
    Map<ElementContainer, ValidationResult> overridenValidationResults =
        runFormValidations(elementResults);
    Map<ElementContainer, ElementResult> correctedElementResults =
        correctElementResults(elementResults, overridenValidationResults);
    boolean formIsValid = checkAllValidationResults(correctedElementResults);

    return new FormResult(this.getId(), correctedElementResults, formIsValid);
  }



  private static List<ElementContainer> packElementsInContainer(Element... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (int i = 0; i < elements.length; i++) {
      ec.add(new ElementContainer(elements[i]));
    }
    return ec;
  }

  // RFE: There might be an api for this!!
  private static List<ElementContainer> packElementContainerInList(ElementContainer... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (int i = 0; i < elements.length; i++) {
      ec.add(elements[i]);
    }
    return ec;
  }


  public Map<ElementContainer, ElementResult> processElements(
      EnvWithSubmitInfo envWithSubmitInfo,
      ElementContainer... elementsToProcess) {
    return this.processElements(envWithSubmitInfo, packElementContainerInList(elementsToProcess));
  }


  public Map<ElementContainer, ElementResult> processElements(
      EnvWithSubmitInfo envWithSubmitInfo,
      List<ElementContainer> elementsToProcess) {
    // check each element
    return processor.processElements(envWithSubmitInfo, elementsToProcess, id);
  }

  private Map<ElementContainer, ValidationResult> runFormValidations(
      Map<ElementContainer, ElementResult> elementResults) {
    // run the form-validators
    Map<ElementContainer, ValidationResult> overridenValidationResults = new LinkedHashMap<>();
    for (FormValidator formValidator : formValidators) {
      overridenValidationResults.putAll(formValidator.validate(elementResults));
    }
    return overridenValidationResults;
  }

  private Map<ElementContainer, ElementResult> correctElementResults(
      Map<ElementContainer, ElementResult> elementResults,
      Map<ElementContainer, ValidationResult> overridenValidationResults) {
    overridenValidationResults.forEach((element, overridenValidationResult) -> {
      ElementResult re = elementResults.get(element);
      elementResults.put(element, re.cloneWithNewValidationResult(overridenValidationResult));
    });
    return elementResults;
  }

  private boolean checkAllValidationResults(
      Map<ElementContainer, ElementResult> correctedElementResults) {
    boolean formIsValid = true;
    for (Map.Entry<ElementContainer, ElementResult> entry : correctedElementResults.entrySet()) {
      if (entry.getValue().getValidationResult() != ValidationResult.ok()) {
        formIsValid = false;
        break;
      }
    }
    return formIsValid;
  }

  public final List<ElementContainer> getElements() {
    return elements;
  }

  public final String getId() {
    return id;
  }

  @SuppressWarnings("serial")
  public class IdenticalElementException extends RuntimeException {

    public IdenticalElementException(ElementContainer container) {
      super("Identical Elements are not allowed. Plese remove double container: "
          + container.element);
    }
  }

}
