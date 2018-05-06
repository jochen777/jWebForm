package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is equal to one of the acceptable values.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Accept implements Criterion {
  private final String[] acceptableValues;
  private boolean caseSensitive = true;


  Accept(String... values) {
    this.acceptableValues = values;
  }

  protected boolean areEqual(String v1, String v2) {
    if (caseSensitive) {
      return v1.equals(v2);
    } else {
      return v1.equalsIgnoreCase(v2);
    }

  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = false;
    for (String v : acceptableValues) {
      if (areEqual(v, value))
        isValid = true;
    }
    if (!isValid) {
      return ValidationResult.fail("jformchecker.allowed_values", (Object[]) acceptableValues);
    }
    return ValidationResult.ok();
  }

  public Criterion ignoreCase() {
    caseSensitive = false;
    return this;
  }


}
