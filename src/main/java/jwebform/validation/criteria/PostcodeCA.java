package jwebform.validation.criteria;

/**
 * Checks that the value conforms to a valid Canadian postcode.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class PostcodeCA extends Regex {
	// NOTE: single space required
	private static final String REGEX = "^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]\\d"
			+ "[ABCEGHJKLMNPRSTVWXYZabceghjklmnprstvwxyz]\\s" + "\\d[ABCEGHJKLMNPRSTVWXYZabceghjklmnprstvwxyz]\\d$";

	PostcodeCA() {
		super(REGEX);
		setErrorMsg("jformchecker.postalcode");
	}

}
