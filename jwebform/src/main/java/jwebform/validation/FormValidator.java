package jwebform.validation;

import jwebform.processor.FieldResults;
import jwebform.processor.FieldValdationResults;

// Validates a complete Form
@FunctionalInterface
public interface FormValidator {

  /**
   * checks a complete form. If something is invalid, associate the validationResult to this fieldType
   * in the resulting map
   * 
   * @param fieldResults fiels of the form to validate
   * @return validation results for the fields
   */
  FieldValdationResults validate(FieldResults fieldResults);
}
