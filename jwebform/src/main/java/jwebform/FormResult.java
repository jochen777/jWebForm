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


  /**
   * Checks, if the Form was submitted AND correctly validated.
   * So if this returns true, the user has submitted the form and every value in it is correct.
   * @return
   */
  public final boolean isOk() {
  public final boolean isSubmittedAndOk() {
    return formIsValid;
  }

  public final boolean isSubmitted() {
    return submitted;
  /**
   * Checks, the form was submitted.
   * If not submitted, it will return true
   * If submitted, it will return false
   *
   * You can use this to show to the user some explanations in case of errors or not.
   *
   *
   * Note: This does not correspond to valdiation errors.
   * If the user submitted and the form is not correct, this will return true.
   * @return
   */
  public final boolean isSubmitted() {
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
