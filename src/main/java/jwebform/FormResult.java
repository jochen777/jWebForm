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

  public View getView(boolean html5Validation) {
    return new View(formId, elementResults, "POST", html5Validation);
  }

  public View getView(boolean html5Validation, String method) {
    return new View(formId, elementResults, method, html5Validation);
  }

  public View getView(String method) {
    return new View(formId, elementResults, method, true);
  }

  public View getView() {
    return new View(formId, elementResults, "POST", true);
  }


  public final Map<ElementContainer, ElementResult> getElementResults() {
    return elementResults;
  }

  public final String getStringValue(String name) {
    // RFE: avoid duplicate code here!
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      if (entry.getValue().getStaticElementInfo().getName().equals(name)) {
        return entry.getValue().getValue();
      }
    }
    throw new RuntimeException(String.format("The element named %s does not exist in form", name));
  }

  public final Object getObectValue(String name) {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      if (entry.getValue().getStaticElementInfo().getName().equals(name)) {
        return entry.getValue().getValueObject();
      }
    }
    throw new RuntimeException(String.format("The element named %s does not exist in form", name));
  }


  public final String debugOutput() {
    StringBuffer b = new StringBuffer();
    elementResults.forEach(
        (k, v) -> b.append("Name:").append(v.getStaticElementInfo().getName()).append(", Value")
            .append(v.getValue()).append("\n").append(v.getValidationResult()).append("\n"));
    return "FormResult" + formIsValid + "\n " + b.toString();
  }


  public String getFormId() {
    return formId;
  }

}
