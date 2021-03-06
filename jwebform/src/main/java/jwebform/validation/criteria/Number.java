package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

import java.util.regex.Pattern;

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
    if (value.length() > 0 && pattern.matcher(value).matches()) {
        return ValidationResult.ok();
    }
    return ValidationResult.fail("jwebform.not_a_number");
  }

}
