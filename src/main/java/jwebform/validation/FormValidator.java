package jwebform.validation;

import java.util.Map;
import jwebform.element.structure.ElementContainer;
import jwebform.processors.ElementResults;
import jwebform.processors.ElementValdationResults;

// Validates a complete Form
@FunctionalInterface
public interface FormValidator {
  /**
   * checks a complete form. If something is invalid, associate the validationResult to this element
   * in the resulting map
   * 
   */
  ElementValdationResults validate(ElementResults elements);
}
