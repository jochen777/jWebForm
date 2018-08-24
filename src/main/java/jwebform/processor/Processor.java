package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.GroupFieldType;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// this is doing the "hard work": Let each element do the apply function, run validations, run
// form-validations
public class Processor {


  // do the processing of the elements, the validation and the form-validation
  public final FieldResults run(EnvWithSubmitInfo envWithSubmitInfo, GroupFieldType group) {
    // call the apply Method
    FieldResults elementResults = processElements(envWithSubmitInfo, group.getChilds());

    // run preprocessors
    elementResults = this.runPostProcessors(elementResults);

    // run the form validators
    ElementValdationResults overridenValidationResults =
        this.runFormValidations(elementResults, group.getValidators(group.of()));

    // if form-validators changed validaiton results, correct them on the elemtns
    return this.correctElementResults(elementResults, overridenValidationResults);
  }



  // process each element. This is used for elements, that have children... (Lke Date-Selects)
  public FieldResults processElements(EnvWithSubmitInfo env,
      Field... elementsToProcess) {
    return this.processElements(env, packElementContainerInList(elementsToProcess));
  }


  private List<PostProcessor> getPostProcessors() {
    return Collections.singletonList(new CheckDoubleElementsPostProcessor());
  }

  private FieldResults runPostProcessors(FieldResults elementResults) {
    for (PostProcessor postProcessor : getPostProcessors()) {
      elementResults = postProcessor.postProcess(elementResults);
    }
    return elementResults;

  }

  private FieldResults processElements(EnvWithSubmitInfo env, List<Field> elements) {
    FieldResults elementResults = new FieldResults();
    for (Field container : elements) {
      if (container.element instanceof GroupFieldType) {
        processGroup(env, elementResults, container);
      } else {
        proessSingleElement(env, elementResults, container);
      }
    }
    return elementResults;
  }

  private void proessSingleElement(
    EnvWithSubmitInfo env, FieldResults elementResults, Field container) {
    // here is where the magic happens! The "apply" method of the elements is called.
    FieldResult result = ((SingleFieldType) container.element).apply(env);
    if (env.isSubmitted()) {
      if (result.getValidationResult() != ValidationResult.undefined()) {
        // element has set the validation itself. This might happen in complex elements. And
        // will
        // override the following validation
        // --- do nothing
      } else {
        result = result.ofValidationResult(container.getValidator().validate(result.getValue()));
      }
    } else {
      // do nothing
    }
    if (elementResults.containsElement(container)) {
      throw new IdenticalElementException(container);
    }
    elementResults.put(container, result);
  }

  private void processGroup(
    EnvWithSubmitInfo env, FieldResults elementResults, Field container) {
    FieldResults groupElementResults = this.run(env, (GroupFieldType) container.element);
    FieldResult groupResult =
        ((GroupFieldType) container.element).process(env, groupElementResults);
    elementResults.put(container, groupResult.cloneWithChilds(groupElementResults));

    // TODO: das eigentliche element (groupElement) brauch auch ein Value. Wie kommt es da dran?
    // Dürfte es auch HTML und ein input-element ausgeben?
    // evtl. ein neues "Ober" Element einführen? Also z.B. Element -> Input, Group
  }

  private ElementValdationResults runFormValidations(
    FieldResults elementResults,
      List<FormValidator> formValidators) {


    // run the form-validators
    ElementValdationResults overridenValidationResults = new ElementValdationResults();
    for (FormValidator formValidator : formValidators) {
      overridenValidationResults.merge(formValidator.validate(elementResults));
    }
    return overridenValidationResults;
  }

  public boolean checkAllValidationResults(FieldResults correctedElementResults) {
    boolean formIsValid = true;
    for (Map.Entry<Field, FieldResult> entry : correctedElementResults) {
      if (entry.getValue().getValidationResult() != ValidationResult.ok()) {
        formIsValid = false;
        break;
      }
    }
    return formIsValid;
  }



  private FieldResults correctElementResults(
    FieldResults elementResults,
    ElementValdationResults overridenValidationResults) {
    overridenValidationResults.getResutls().forEach((element, overridenValidationResult) -> {
      FieldResult re = elementResults.get(element);
      elementResults.put(element, re.cloneWithNewValidationResult(overridenValidationResult));
    });
    return elementResults;
  }


  @SuppressWarnings("serial")
  public class IdenticalElementException extends RuntimeException {

    public IdenticalElementException(Field container) {
      super("Identical Elements are not allowed. Plese remove double container: "
          + container.element);
    }
  }

  private static List<Field> packElementContainerInList(Field... elements) {
    List<Field> ec = new ArrayList<>();
    Collections.addAll(ec, elements);
    return ec;
  }



}
