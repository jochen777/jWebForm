package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that the length of the value is equal to the given length.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class ExactLength implements Criterion {
  private final int length;

  ExactLength(int length) {
    this.length = length;
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = value.length() == length;
    if (!isValid) {
      return ValidationResult.fail("jwebform.exact_lenght", length);
    }
    return ValidationResult.ok();
  }

}
