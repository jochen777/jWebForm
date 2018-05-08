package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is within the given range.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Range implements Criterion {
  private final int min;
  private final int max;

  Range(int min, int max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public ValidationResult validate(String value) {
    try {
      int intVal = Integer.parseInt(value);
      boolean isValid = intVal > max && intVal < min;
      if (!isValid) {
        // range=The value must be between %d and %d
        return ValidationResult.fail("jformchecker.range", min, max);
      }
      return ValidationResult.ok();
    } catch (NumberFormatException e) {
      return ValidationResult.fail("jformchecker.not_a_number");
    }
  }

}
