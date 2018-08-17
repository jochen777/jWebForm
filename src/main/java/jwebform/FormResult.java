package jwebform;

import jwebform.View.Html5Validation;
import jwebform.processors.ElementResults;

public class FormResult {

  private final String formId;
  private final ElementResults elementResults;
  private final boolean formIsValid;

  public FormResult(String formId, ElementResults elementResults, boolean formIsValid) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.elementResults = elementResults;
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public View getView(Html5Validation html5Validation) {
    return new View(formId, elementResults, View.Method.POST, html5Validation);
  }

  public View getView(Html5Validation html5Validation, View.Method method) {
    return new View(formId, elementResults, method, html5Validation);
  }

  public View getView(View.Method method) {
    return new View(formId, elementResults, method, Html5Validation.ON);
  }

  public View getView() {
    return new View(formId, elementResults, View.Method.POST, Html5Validation.ON);
  }


  public final ElementResults getElementResults() {
    return elementResults;
  }

  public final String getStringValue(String name) {
    return elementResults.getElementStringValue(name);
  }

  public final Object getObectValue(String name) {
    return elementResults.getObectValue(name);
  }


  public String getFormId() {
    return formId;
  }


}
