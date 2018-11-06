package jwebform.validation.criteria;

/**
 * Checks that the value conforms to the url format.
 * 
 */
public final class Url extends Regex {
  private static final String REGEX = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";

  Url() {
    super(REGEX, false);
    setErrorMsg("jwebform.valid_url");
  }



}
