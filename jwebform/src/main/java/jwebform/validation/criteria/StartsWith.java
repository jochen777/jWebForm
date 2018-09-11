package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if value starts with the given string.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class StartsWith implements Criterion {
  private final String[] prefixes;

  StartsWith(String... prefixes) {
    this.prefixes = prefixes;
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = false;
    for (String prefix : prefixes) {
      if (value.startsWith(prefix))
        isValid = true;
    }

    if (!isValid) {
      return ValidationResult.fail("jwebform.starts_with", (Object[]) prefixes);
    }
    return ValidationResult.ok();
  }

}
