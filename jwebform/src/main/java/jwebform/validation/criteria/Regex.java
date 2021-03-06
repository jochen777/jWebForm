package jwebform.validation.criteria;

import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;

import java.util.regex.Pattern;

/**
 * Checks if a string matches a regular expression.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Regex implements Criterion {
  private final Pattern pattern;
  private final String stringPattern;
  private String errorMsg = "jwebform.regexp";

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  protected Regex(String pattern, boolean considerCase) {
    this.stringPattern = pattern;
    if (!considerCase) {
      this.pattern = Pattern.compile(pattern);
    } else {
      this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }
  }

  Regex(String pattern) {
    this(pattern, true);
  }

  public Regex ignoreCose() {
    return new Regex(stringPattern, false);
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
