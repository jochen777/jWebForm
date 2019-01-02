package jwebform;

import jwebform.FormModel.Html5Validation;
import jwebform.model.FormModelBuilder;
import jwebform.processor.FieldResults;
import jwebform.resultprocessor.ModelResultProcessor;
import jwebform.resultprocessor.ResultProcessorBuilder;

/**
 * The result of a form.run It contains all infos, that is needed to get the entered values, the
 * validation results and if the form was submitted correctly.
 */
public class FormResult {

  private final String formId;
  private final FieldResults fieldResults;
  protected final boolean formIsValid;
  private final boolean isFirstRun;


  private final ResultProcessorBuilder resultProcessorBuilder;

  @Deprecated
  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun,  FormModelBuilder formModelBuilder) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
    this.resultProcessorBuilder = ModelResultProcessor::new;
  }


  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun, ResultProcessorBuilder resultProcessorBuilder) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
    this.resultProcessorBuilder = resultProcessorBuilder;
  }


  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.isFirstRun = isFirstRun;
    this.resultProcessorBuilder = ModelResultProcessor::new;
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


  public ModelResultProcessor getResultProcessor() {
    return resultProcessorBuilder.build(this);
  }


  @Deprecated
  public FormModel getFormModel(Html5Validation html5Validation) {
    return getResultProcessor().getFormModel(FormModel.Method.POST, html5Validation);
  }

  @Deprecated
  public FormModel getFormModel(Html5Validation html5Validation, FormModel.Method method) {
    return getResultProcessor().getFormModel( method, html5Validation);
  }

  @Deprecated
  public FormModel getFormModel(FormModel.Method method) {
    return getResultProcessor().getFormModel( method, Html5Validation.ON);
  }

  @Deprecated
  public FormModel getFormModel() {
    return getResultProcessor().getFormModel();
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
