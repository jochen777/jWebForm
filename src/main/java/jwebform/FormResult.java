package jwebform;

import java.util.Map;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.view.Theme;

public final class FormResult {

  private final String formId;
  private final Map<ElementContainer, ElementResult> elementResults;
  private final boolean formIsValid;

  public FormResult(String formId, Map<ElementContainer, ElementResult> elementResults,
      boolean formIsValid) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.elementResults = elementResults;
  }

  public boolean isOk() {
    return formIsValid;
  }

  public View getView() {
    return new View(formId, elementResults);
  }

  public View getView(Theme theme) {
    return new View(formId, elementResults, theme);
  }


  public Map<ElementContainer, ElementResult> getElementResults() {
    return elementResults;
  }

  public String debugOutput() {
    StringBuffer b = new StringBuffer();
    elementResults.forEach(
        (k, v) -> b.append("Value" + v.getValue() + "\n" + v.getValidationResult() + "\n"));
    return "FormResult" + formIsValid + "\n " + b.toString();
  }

  
  public static class FormResultFactory {
    public FormResult of(String formId, Map<ElementContainer, ElementResult> elements, boolean valid) {
      return new FormResult(formId, elements, valid);
    }
  }

}
