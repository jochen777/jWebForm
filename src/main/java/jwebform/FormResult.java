package jwebform;

import jwebform.View.Html5Validation;
import jwebform.processor.FieldResults;

public class FormResult {

  private final String formId;
  private final FieldResults fieldResults;
  private final boolean formIsValid;

  public FormResult(String formId, FieldResults fieldResults, boolean formIsValid) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.fieldResults = fieldResults;
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public View getView(Html5Validation html5Validation) {
    return new View(formId, fieldResults, View.Method.POST, html5Validation);
  }

  public View getView(Html5Validation html5Validation, View.Method method) {
    return new View(formId, fieldResults, method, html5Validation);
  }

  public View getView(View.Method method) {
    return new View(formId, fieldResults, method, Html5Validation.ON);
  }

  public View getView() {
    return new View(formId, fieldResults, View.Method.POST, Html5Validation.ON);
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
