package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that the length of the value is within the given range.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Length implements Criterion {
  private final int min;
  private final int max;

  Length(int min, int max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = value.length() <= max && value.length() >= min;
    if (!isValid) {
      return ValidationResult.fail("jwebform.length", min, max);
    }
    return ValidationResult.ok();

  }

}
