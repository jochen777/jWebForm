package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is not greater than the specified maximum.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class MaxLength implements Criterion {
  private final int maxLengthOfInput;

  MaxLength(int maxLength) {
    this.maxLengthOfInput = maxLength;
  }

  public int getMaxLength() {
    return maxLengthOfInput;
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = value.length() <= maxLengthOfInput;
    if (!isValid) {
      return ValidationResult.fail("jformchecker.max_len", maxLengthOfInput);
    }
    return ValidationResult.ok();
  }

}
