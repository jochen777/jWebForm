package jwebform.validation.criteria;

import jwebform.validation.ValidationResult;

/**
 * Checks that the value is less than or equal to the given {@link Comparable}.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Max extends AbstractNumberComparingCriterion {
  private final int maxValue;

  Max(int max) {
    this.maxValue = max;
  }

  @Override
  public ValidationResult validateNumberAndSetError(int input) {
    boolean isValid = input < maxValue;
    if (!isValid) {
      return ValidationResult.fail("jwebform.max", maxValue);
    }
    return ValidationResult.ok();
  }

}
