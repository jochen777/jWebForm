package jwebform.validation;

import jwebform.processors.ElementResults;
import jwebform.processors.ElementValdationResults;

// Validates a complete Form
@FunctionalInterface
public interface FormValidator {

  /**
   * checks a complete form. If something is invalid, associate the validationResult to this element
   * in the resulting map
   * 
   * @param elements Elements of the form to validate
   * @return validation results for the elements
   */
  ElementValdationResults validate(ElementResults elements);
}
