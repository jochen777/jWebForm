package jwebform.validation.criteria;

import jwebform.validation.ValidationResult;

/**
 * Checks that value is greater than or equal to the given {@link Comparable}.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Min extends AbstractNumberComparingCriterion {
  private final int minValue;

  Min(int min) {
    this.minValue = min;
  }

  @Override
  public ValidationResult validateNumberAndSetError(int input) {
    boolean isValid = input > minValue;
    if (!isValid) {
      return ValidationResult.fail("jformchecker.min", minValue);
    }
    return ValidationResult.ok();
  }

}
