package jwebform.validation.criteria;

/**
 * Checks that the value conforms to the U.S. ZIP code format.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class ZipCode extends Regex {
  private static final String REGEX = "^\\d{5}(-\\d{4})?$";

  ZipCode() {
    super(REGEX);
  }

  protected String generateErrorMessage() {
    return "jwebform.zip";
  }

}
