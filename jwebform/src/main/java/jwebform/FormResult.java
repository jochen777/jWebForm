package jwebform;

import jwebform.FormModel.Html5Validation;
import jwebform.model.FormModelBuilder;
import jwebform.processor.FieldResults;
import jwebform.resultprocessor.ModelResultProcessor;
import jwebform.resultprocessor.ResultProcessor;

/**
 * The result of a form.run It contains all infos, that is needed to get the entered values, the
 * validation results and if the form was submitted correctly.
 */
public class FormResult {

  private final String formId;
  private final FieldResults fieldResults;
  protected final boolean formIsValid;
  private final boolean isFirstRun;



  @Deprecated
  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun, FormModelBuilder formModelBuilder) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
  }


  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
  }

  @Override
  public String toString() {
    return String.format("Formid: %s, Valid: %b, Field-Results: %s", formId, formIsValid,
        fieldResults);
  }


  /**
   * Checks, if the Form was submitted AND correctly validated. So if this returns true, the user
   * has submitted the form and every value in it is correct.
   * 
   * @return
   */
  public final boolean isValid() {
    return formIsValid;
  }



  // Please use isValid (This name is much more readable)
  @Deprecated
  public final boolean isOK() {
    return formIsValid;
  }


  /**
   * Checks, the form was submitted. If not submitted, it will return true If submitted, it will
   * return false
   *
   * You can use this to show to the user some explanations in case of errors or not.
   *
   *
   * Note: This does not correspond to valdiation errors. If the user submitted and the form is not
   * correct, this will return true.
   * 
   * @return
   */
  public final boolean isSubmitted() {
    return !isFirstRun;
  }


  public <T> T process(ResultProcessor<T> resultProcessor) {
    return resultProcessor.process(this);
  }

  @Deprecated // use new FormModel(FormResult formResult, Method method, Html5Validation
              // html5Validation) ... instead
  public FormModel getFormModel(Html5Validation html5Validation, FormModel.Method method) {
    ModelResultProcessor modelResultProcessor = new ModelResultProcessor(method, html5Validation);
    return process(modelResultProcessor);
  }

  @Deprecated
  public FormModel getFormModel(Html5Validation html5Validation) {
    return getFormModel(html5Validation, FormModel.Method.POST);
  }


  @Deprecated
  public FormModel getFormModel(FormModel.Method method) {
    return getFormModel(Html5Validation.ON, method);
  }

  @Deprecated
  public FormModel getFormModel() {
    return getFormModel(Html5Validation.ON, FormModel.Method.POST);
  }


  public final FieldResults getFieldResults() {
    return fieldResults;
  }

  public final String getStringValue(String name) {
    return fieldResults.getFieldStringValue(name);
  }

  public final Object getObectValue(String name) {
    return fieldResults.getObjectValue(name);
  }


  public String getFormId() {
    return formId;
  }


}
