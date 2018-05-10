package jwebform.validation.criteria;

/**
 * Checks that the value conforms to the email address format.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Email extends Regex {
  private static final String REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

  Email() {
    super(REGEX, false);
    setErrorMsg("jformchecker.valid_email");
  }



}
