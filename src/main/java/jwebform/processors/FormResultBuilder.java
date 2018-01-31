package jwebform.processors;

import java.util.Map;

import jwebform.FormResult;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

// builds formResults. Allows to crreate subclassed FormResults by overriding
public class FormResultBuilder {

  public FormResult build(
      String formId,
      Map<ElementContainer, ElementResult> elementResults,
      boolean formIsValid) {
    return new FormResult(formId, elementResults, formIsValid);

  }
}
