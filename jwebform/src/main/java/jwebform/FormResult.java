package jwebform;

import jwebform.FormModel.Html5Validation;
import jwebform.processor.FieldResults;
import jwebform.model.FormModelBuilder;

/**
 * The result of a form.run It contains all infos, that is needed to get the entered values, the
 * validation results and if the form was submitted correctly.
 */
public class FormResult {

  private final String formId;
  private final FieldResults fieldResults;
  private final boolean formIsValid;
  private final FormModelBuilder formModelBuilder;
  private final boolean isFirstRun;


  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid, boolean isFirstRun,
      FormModelBuilder formModelBuilder) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
    this.formModelBuilder = formModelBuilder;
  }

  @Override
  public String toString() {
    return String.format("Formid: %s, Valid: %b, Field-Results: %s", formId, formIsValid,
        fieldResults);
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public final boolean isFirstRun() {
    return isFirstRun;
  }


  public FormModel getFormModel(Html5Validation html5Validation) {
    return formModelBuilder.build(formId, fieldResults, FormModel.Method.POST, html5Validation);
  }

  public FormModel getFormModel(Html5Validation html5Validation, FormModel.Method method) {
    return formModelBuilder.build(formId, fieldResults, method, html5Validation);
  }

  public FormModel getFormModel(FormModel.Method method) {
    return formModelBuilder.build(formId, fieldResults, method, Html5Validation.ON);
  }

  public FormModel getFormModel() {
    return formModelBuilder.build(formId, fieldResults, FormModel.Method.POST, Html5Validation.ON);
  }


  public final FieldResults getFieldResults() {
    return fieldResults;
  }

  public final String getStringValue(String name) {
    return fieldResults.getFieldStringValue(name);
  }

  public final Object getObectValue(String name) {
    return fieldResults.getObectValue(name);
  }


  public String getFormId() {
    return formId;
  }


}
