package jwebform.validation;

import jwebform.processor.FieldResults;
import jwebform.processor.FieldValdationResults;

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
  FieldValdationResults validate(FieldResults elements);
}
