package jwebform.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

// this is doing the "hard work": Let each element do the apply function, run validations, run
// form-validations
public class Processor {


  // do the processing of the elements, the validation and the form-validation
  public final ElementResults run(EnvWithSubmitInfo envWithSubmitInfo, GroupType group) {
    // call the apply Method
    ElementResults elementResults = processElements(envWithSubmitInfo, group.getChilds());

    // run preprocessors
    elementResults = this.runPostProcessors(elementResults);

    // run the form validators
    Map<ElementContainer, ValidationResult> overridenValidationResults =
        this.runFormValidations(elementResults, group.getValidators(group.of()));

    // if form-validators changed validaiton results, correct them on the elemtns
    return this.correctElementResults(elementResults, overridenValidationResults);
  }



  // process each element. This is used for elements, that have children... (Lke Date-Selects)
  public ElementResults processElements(EnvWithSubmitInfo env,
      ElementContainer... elementsToProcess) {
    return this.processElements(env, packElementContainerInList(elementsToProcess));
  }


  private List<PostProcessor> getPostProcessors() {
    return Collections.singletonList(new CheckDoubleElementsPostProcessor());
  }

  private ElementResults runPostProcessors(ElementResults elementResults) {
    for (PostProcessor postProcessor : getPostProcessors()) {
      elementResults = postProcessor.postProcess(elementResults);
    }
    return elementResults;

  }

  private ElementResults processElements(EnvWithSubmitInfo env, List<ElementContainer> elements) {
    ElementResults elementResults = new ElementResults();
    for (ElementContainer container : elements) {


      if (container.element instanceof GroupType) {
        ElementResults groupElementResults = this.run(env, (GroupType) container.element);
        ElementResult groupResult =
            ((GroupType) container.element).process(env, groupElementResults);
        elementResults.put(container, groupResult.cloneWithChilds(groupElementResults));

        // TODO: das eigentliche element (groupElement) brauch auch ein Value. Wie kommt es da dran?
        // Dürfte es auch HTML und ein input-element ausgeben?
        // evtl. ein neues "Ober" Element einführen? Also z.B. Element -> Input, Group
      } else {
        // here is where the magic happens! The "apply" method of the elements is called.
        ElementResult result = ((SingleType) container.element).apply(env);
        if (env.isSubmitted()) {
          if (result.getValidationResult() != ValidationResult.undefined()) {
            // element has set the validation itself. This might happen in complex elements. And
            // will
            // override the following validation
            // --- do nothing
          } else {
            result = result.ofValidationResult(container.validator.validate(result.getValue()));
          }
        } else {
          // do nothing
        }
        if (elementResults.containsElement(container)) {
          throw new IdenticalElementException(container);
        }
        elementResults.put(container, result);
      }
    }
    return elementResults;
  }

  private Map<ElementContainer, ValidationResult> runFormValidations(ElementResults elementResults,
      List<FormValidator> formValidators) {


    // run the form-validators
    Map<ElementContainer, ValidationResult> overridenValidationResults = new LinkedHashMap<>();
    for (FormValidator formValidator : formValidators) {
      overridenValidationResults.putAll(formValidator.validate(elementResults));
    }
    return overridenValidationResults;
  }

  public boolean checkAllValidationResults(ElementResults correctedElementResults) {
    boolean formIsValid = true;
    for (Map.Entry<ElementContainer, ElementResult> entry : correctedElementResults) {
      if (entry.getValue().getValidationResult() != ValidationResult.ok()) {
        formIsValid = false;
        break;
      }
    }
    return formIsValid;
  }



  private ElementResults correctElementResults(ElementResults elementResults,
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
    Collections.addAll(ec, elements);
    return ec;
  }



}
