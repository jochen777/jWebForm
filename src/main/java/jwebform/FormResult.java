package jwebform;

import java.util.Map.Entry;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
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

  public View getView(boolean html5Validation) {
    return new View(formId, elementResults, View.Method.POST, html5Validation);
  }

  public View getView(boolean html5Validation, View.Method method) {
    return new View(formId, elementResults, method, html5Validation);
  }

  public View getView(View.Method method) {
    return new View(formId, elementResults, method, true);
  }

  public View getView() {
    return new View(formId, elementResults, View.Method.POST, true);
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


  public final String debugOutput() {
    StringBuffer b = new StringBuffer();
    for (Entry<ElementContainer, ElementResult> elem : elementResults) {
      b.append("Name:").append(elem.getValue().getStaticElementInfo().getName()).append(", Value")
          .append(elem.getValue().getValue()).append("\n")
          .append(elem.getValue().getValidationResult()).append("\n");
    }
    return "FormResult" + formIsValid + "\n " + b.toString();
  }


  public String getFormId() {
    return formId;
  }


}
