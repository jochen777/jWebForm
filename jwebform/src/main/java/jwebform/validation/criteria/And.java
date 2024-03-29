package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Performs an <b>AND</b> over all criteria on the given value.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class And implements Criterion {
  private final Criterion[] criteria;

  And(Criterion... criteria) {
    if (criteria.length < 2)
      throw new IllegalArgumentException(getClass().getName() + " requires at least two criteria");

    this.criteria = criteria;
  }

  @Override
  public ValidationResult validate(String value) {
    for (Criterion criterion : criteria) {
      ValidationResult vr = criterion.validate(value);
      if (!vr.isValid) {
        return vr;
      }
    }
    // everything ok
    return ValidationResult.ok();
  }

}
