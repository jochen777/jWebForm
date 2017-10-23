package jwebform.validation;

import java.util.Map;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;

// Validates a complete Form
@FunctionalInterface
public interface FormValidator {
  /**
   * checks a complete form. If something is invalid, associate the validationResult to this element
   * in the resulting map
   * 
   * @param env
   * @param form
   * @return
   */
  public Map<Element, ValidationResult> validate(Map<Element, ElementResult> elements);
}
