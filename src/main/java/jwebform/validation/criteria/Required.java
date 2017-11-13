package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if value starts with the given string.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Required implements Criterion {

  @Deprecated
  public static Required INSTANCE = new Required();
  public static Required instance;

  private Required() {}

  public Required getInstance() {
    if (instance == null) {
      instance = new Required();
    }
    return instance;
  }

  @Override
  public ValidationResult validate(String value) {
    if ("".equals(value.trim())) {
      return ValidationResult.fail("jformchecker.required");
    }
    return ValidationResult.ok();
  }

}
