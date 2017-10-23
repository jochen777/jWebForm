package jwebform.validation.criteria;

import java.util.regex.Pattern;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks that value is a number.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Number implements Criterion {

  Number() {}

  static Pattern pattern = Pattern.compile("[0-9]+");

  @Override
  public ValidationResult validate(String value) {
    if (value.length() > 0) {
      if (pattern.matcher(value).matches()) {
        return ValidationResult.ok();
      }
    }
    return ValidationResult.fail("jformchecker.not_a_number");
  }

}
