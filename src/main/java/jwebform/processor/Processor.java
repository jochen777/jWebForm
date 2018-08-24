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

// this is doing the "hard work": Let each field do the apply function, run validations, run
// form-validations
public class Processor {


  // do the processing of the field, the validation and the form-validation
  public final FieldResults run(EnvWithSubmitInfo envWithSubmitInfo, GroupFieldType group) {
    // call the apply Method
    FieldResults fieldResults = processFields(envWithSubmitInfo, group.getChilds());

    // run preprocessors
    fieldResults = this.runPostProcessors(fieldResults);

    // run the form validators
    FieldValdationResults overridenValidationResults =
        this.runFormValidations(fieldResults, group.getValidators(group.of()));

    // if form-validators changed validaiton results, correct them on the elemtns
    return this.correctFieldResults(fieldResults, overridenValidationResults);
  }



  // process each field. This is used for fields, that have children... (Lke Date-Selects)
  public FieldResults processFields(EnvWithSubmitInfo env,
      Field... fieldsToProcess) {
    return this.processFields(env, packFieldsInList(fieldsToProcess));
  }


  private List<PostProcessor> getPostProcessors() {
    return Collections.singletonList(new CheckDoubleFieldsPostProcessor());
  }

  private FieldResults runPostProcessors(FieldResults fieldResults) {
    for (PostProcessor postProcessor : getPostProcessors()) {
      fieldResults = postProcessor.postProcess(fieldResults);
    }
    return fieldResults;

  }

  private FieldResults processFields(EnvWithSubmitInfo env, List<Field> fields) {
    FieldResults fieldResults = new FieldResults();
    for (Field container : fields) {
      if (container.element instanceof GroupFieldType) {
        processGroup(env, fieldResults, container);
      } else {
        processSingleType(env, fieldResults, container);
      }
    }
    return fieldResults;
  }

  private void processSingleType(
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

  private FieldValdationResults runFormValidations(
    FieldResults elementResults,
      List<FormValidator> formValidators) {


    // run the form-validators
    FieldValdationResults overridenValidationResults = new FieldValdationResults();
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



  private FieldResults correctFieldResults(
    FieldResults elementResults,
    FieldValdationResults overridenValidationResults) {
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

  private static List<Field> packFieldsInList(Field... elements) {
    List<Field> ec = new ArrayList<>();
    Collections.addAll(ec, elements);
    return ec;
  }



}
