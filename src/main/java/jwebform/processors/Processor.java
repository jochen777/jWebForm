package jwebform.processors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jwebform.FormResult;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

public final class Processor {


  public List<PostProcessor> getPostProcessors() {
    return Arrays.asList(new CheckDoubleElementsPostProcessor());
  }

  public final Map<ElementContainer, ElementResult> runPostProcessors(
      Map<ElementContainer, ElementResult> elementResults) {
    for (PostProcessor postProcessor : getPostProcessors()) {
      elementResults = postProcessor.postProcess(elementResults);
    }
    return elementResults;

  }
  
  public final FormResult run(Env env, String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
    // validate form
    Map<ElementContainer, ElementResult> elementResults =
        processElements(env.getEnvWithSumitInfo(id, this), elements);
    elementResults = this.runPostProcessors(elementResults);
    Map<ElementContainer, ValidationResult> overridenValidationResults =
        this.runFormValidations(elementResults, formValidators);
    Map<ElementContainer, ElementResult> correctedElementResults =
        this.correctElementResults(elementResults, overridenValidationResults);
    boolean formIsValid = this.checkAllValidationResults(correctedElementResults);

    return new FormResult(id, correctedElementResults, formIsValid);
  }

  public final Map<ElementContainer, ElementResult> processElements(
      EnvWithSubmitInfo env,
      List<ElementContainer> elements
      ) {
    Map<ElementContainer, ElementResult> elementResults = new LinkedHashMap<>();
    for (ElementContainer container : elements) {
      // here is where the magic happens! The "apply" method of the elements is called.
      ElementResult result = container.element.apply(env);
      if (env.isSubmitted()) {
        if (result.getValidationResult() != ValidationResult.undefined()) {
          // element has set the validation itself. This might happen in complex elements. And will
          // override the following validation
          // --- do nothing
        } else {
          if (container.validator != null) {
            result = result.ofValidationResult(container.validator.validate(result.getValue()));
          } else {
            result = result.ofValidationResult(ValidationResult.ok());
          }
        }
      } else {
        // do nothing
      }
      if (elementResults.containsKey(container)) {
        throw new IdenticalElementException(container);
      }
      elementResults.put(container, result);
    }
    return elementResults;
  }
  
  public final Map<ElementContainer, ValidationResult> runFormValidations(
      Map<ElementContainer, ElementResult> elementResults, List<FormValidator> formValidators) {
    // run the form-validators
    Map<ElementContainer, ValidationResult> overridenValidationResults = new LinkedHashMap<>();
    for (FormValidator formValidator : formValidators) {
      overridenValidationResults.putAll(formValidator.validate(elementResults));
    }
    return overridenValidationResults;
  }
  
  public final boolean checkAllValidationResults(
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


  
  public final Map<ElementContainer, ElementResult> correctElementResults(
      Map<ElementContainer, ElementResult> elementResults,
      Map<ElementContainer, ValidationResult> overridenValidationResults) {
    overridenValidationResults.forEach((element, overridenValidationResult) -> {
      ElementResult re = elementResults.get(element);
      elementResults.put(element, re.cloneWithNewValidationResult(overridenValidationResult));
    });
    return elementResults;
  }


  @SuppressWarnings("serial")
  public class IdenticalElementException extends RuntimeException {

    public IdenticalElementException(ElementContainer container) {
      super("Identical Elements are not allowed. Plese remove double container: "
          + container.element);
    }
  }
  
  private static List<ElementContainer> packElementContainerInList(ElementContainer... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (int i = 0; i < elements.length; i++) {
      ec.add(elements[i]);
    }
    return ec;
  }


  public Map<ElementContainer, ElementResult> processElements(
      EnvWithSubmitInfo env,
      ElementContainer... elementsToProcess) {
    return this.processElements(env,  packElementContainerInList(elementsToProcess));
  }
}
