package jwebform;

import java.util.Map;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.transfer.TransfererFromFormResult;
import jwebform.view.Theme;

public class FormResult {

  private final String formId;
  private final Map<ElementContainer, ElementResult> elementResults;
  private final boolean formIsValid;
  private final TransfererFromFormResult transferer;

  public FormResult(String formId, Map<ElementContainer, ElementResult> elementResults,
      boolean formIsValid, TransfererFromFormResult transferer) {
    this.formId = formId;
    this.formIsValid = formIsValid;
    this.elementResults = elementResults;
    this.transferer = transferer;
  }


  public final boolean isOk() {
    return formIsValid;
  }

  public View getView() {
    return new View(formId, elementResults);
  }

  public View getView(Theme theme) {
    return new View(formId, elementResults, theme);
  }


  public final Map<ElementContainer, ElementResult> getElementResults() {
    return elementResults;
  }

  public final String debugOutput() {
    StringBuffer b = new StringBuffer();
    elementResults.forEach(
        (k, v) -> b.append("Value" + v.getValue() + "\n" + v.getValidationResult() + "\n"));
    return "FormResult" + formIsValid + "\n " + b.toString();
  }


  public final TransfererFromFormResult getTransferer() {
    return transferer;
  }

}
