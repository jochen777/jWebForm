package jwebform.validation;

/**
 * Holds the result of a validation including the possible error-message
 * 
 * @author jochen
 *
 */
public class ValidationResult {

	public boolean isValid = false;
	
	// caching:
	private static ValidationResult okResult =  new ValidationResult(true, "", null, null);
	private static ValidationResult undefinedResult =  new ValidationResult(false, "", null, null);
	
	
	public boolean isValid() {
		return isValid;
	}

	public String getMessage() {
		return message;
	}

	public Object[] getErrorVals() {
		return errorVals;
	}

	String message;
	String translatedMessage;
	Object[] errorVals;

	public ValidationResult(boolean isValid, String message, Object[] errorVals, String translatedMessage) {
		this.isValid = isValid;
		this.message = message;
		this.errorVals = errorVals;
		this.translatedMessage = translatedMessage;
	}

	// factory methods
	public static ValidationResult of_(boolean isValid, String message, Object... errorVals) {
		return new ValidationResult(isValid, message, errorVals, null);
	}

	public static ValidationResult fail(String message, Object... errorVals) {
		return new ValidationResult(false, message, errorVals, null);
	}

	public static ValidationResult failWithTranslated(String message, Object... errorVals) {
		return new ValidationResult(false, null, errorVals, message);
	}

	public static ValidationResult failWithTranslated(String message) {
		return new ValidationResult(false, null, new Object[0], message);
	}
	
	public static ValidationResult ok() {
		return okResult;
	}

	public static ValidationResult undefined() {
		return undefinedResult;
	}

	
	public String getTranslatedMessage() {
		return translatedMessage;
	}

}
