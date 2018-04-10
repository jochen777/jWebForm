package jwebform;

import java.util.Map;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

public class FormResult {

  private final String formId;
  private final Map<ElementContainer, ElementResult> elementResults;
  private final boolean formIsValid;

  public FormResult(String formId, Map<ElementContainer, ElementResult> elementResults,
      boolean formIsValid) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.elementResults = elementResults;
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public View getView(String get, boolean html5Validation) {
    return new View(formId, elementResults, "GET", html5Validation);
  }


  public final Map<ElementContainer, ElementResult> getElementResults() {
    return elementResults;
  }

  public final String debugOutput() {
    StringBuffer b = new StringBuffer();
    elementResults.forEach((k, v) -> b.append("Name:" + v.getStaticElementInfo().getName()
        + ", Value" + v.getValue() + "\n" + v.getValidationResult() + "\n"));
    return "FormResult" + formIsValid + "\n " + b.toString();
  }


  public String getFormId() {
    return formId;
  }

  public View getView(String method) {
    return new View(formId, elementResults, method, true);
  }
}
