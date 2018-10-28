package jwebform;

import jwebform.View.Html5Validation;
import jwebform.processor.FieldResults;
import jwebform.view.ViewBuilder;

/**
 * The result of a form.run It contains all infos, that is needed to get the entered values, the
 * validation results and if the form was submitted correctly.
 */
public class FormResult {

  private final String formId;
  private final FieldResults fieldResults;
  private final boolean formIsValid;
  private final ViewBuilder viewGenerator;

  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      ViewBuilder viewGenerator) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
    this.viewGenerator = viewGenerator;
  }

  @Override
  public String toString() {
    return String.format("Formid: %s, Valid: %b, Field-Results: %s", formId, formIsValid,
        fieldResults);
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public View getView(Html5Validation html5Validation) {
    return viewGenerator.build(formId, fieldResults, View.Method.POST, html5Validation);
  }

  public View getView(Html5Validation html5Validation, View.Method method) {
    return viewGenerator.build(formId, fieldResults, method, html5Validation);
  }

  public View getView(View.Method method) {
    return viewGenerator.build(formId, fieldResults, method, Html5Validation.ON);
  }

  public View getView() {
    return viewGenerator.build(formId, fieldResults, View.Method.POST, Html5Validation.ON);
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
