package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is not less than the specified minimum.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class MinLength implements Criterion {
  private final int minLengthValue;

  MinLength(int minLength) {
    this.minLengthValue = minLength;
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = value.length() >= minLengthValue;
    if (!isValid) {
      return ValidationResult.fail("jformchecker.min_len", minLengthValue);
    }
    return ValidationResult.ok();
  }

}
