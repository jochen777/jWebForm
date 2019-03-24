package jwebform.validation.criteria;

/**
 * Checks that the value conforms to the email address format.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class Email extends Regex {
  private static final String REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

  Email() {
    super(REGEX, false);
    setErrorMsg("jwebform.valid_email");
  }



}
