package jwebform.factories;

import java.util.Map;
import jwebform.FormResult;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

public class FormResultFactory {
  public FormResult of(String formId, Map<ElementContainer, ElementResult> elements, boolean valid) {
    return new FormResult(formId, elements, valid);
  }
}
