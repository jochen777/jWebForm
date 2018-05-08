package jwebform.validation.criteria;

import java.util.regex.Pattern;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

/**
 * Checks if a string matches a regular expression.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Regex implements Criterion {
  private final Pattern pattern;
  private String errorMsg = "jformchecker.regexp";

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  Regex(String pattern) {
    this.pattern = Pattern.compile(pattern);
  }

  @Override
  public ValidationResult validate(String value) {
    boolean isValid = pattern.matcher(value).find();
    if (!isValid) {
      return ValidationResult.fail(errorMsg);
    }
    return ValidationResult.ok();
  }

}
