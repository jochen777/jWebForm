package jwebform.validation.criteria;

/**
 * Checks that the value conforms to the email address format.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Email extends CaseInsensitiveRegex {
  private static final String REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

  Email() {
    super(REGEX);
    setErrorMsg("jformchecker.valid_email");
  }



}
