package jwebform.validation;

/**
 * Holds the result of a validation including the possible error-message
 * 
 * @author jochen
 *
 */
public class ValidationResult {

  public final boolean isValid;
  private static Object[] noErrorVals = new Object[0];
  private static String emptyTranslatedMsg = "";
  private static String emptyMsg = "";

  // caching:
  private static ValidationResult okResult =
      new ValidationResult(true, "", noErrorVals, emptyTranslatedMsg);
  private static ValidationResult undefinedResult =
      new ValidationResult(false, "", noErrorVals, emptyTranslatedMsg);

  public boolean isValid() {
    return isValid;
  }

  // Will return either the translated message (if set) or the messageKey
  // RFE: This normally makes no sense. Either you want the key OR the translated message.
  @Deprecated // Use either getMessageKey OR getTranslatedMessage
  public String getMessage() {
    return translatedMessage.length()>0?translatedMessage: message;
  }

  public Object[] getErrorVals() {
    return errorVals;
  }

  String message;
  String translatedMessage;
  Object[] errorVals;

  public ValidationResult(boolean isValid, String message, Object[] errorVals,
      String translatedMessage) {
    this.isValid = isValid;
    this.message = message;
    this.errorVals = errorVals;
    this.translatedMessage = translatedMessage;
  }

  public String getMessageKey() {
    return message;
  }



  // factory methods
  public static ValidationResult of_(boolean isValid, String message, Object... errorVals) {
    return new ValidationResult(isValid, message, errorVals, emptyTranslatedMsg);
  }

  public static ValidationResult fail(String message, Object... errorVals) {
    return new ValidationResult(false, message, errorVals, emptyTranslatedMsg);
  }

  public static ValidationResult failWithTranslated(String message, Object... errorVals) {
    return new ValidationResult(false, emptyMsg, errorVals, message);
  }

  public static ValidationResult failWithTranslated(String message) {
    return new ValidationResult(false, emptyMsg, noErrorVals, message);
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

  public boolean isSuccess() {
    return this != ValidationResult.undefined() && this.isValid;
  }

  public boolean isError() {
    return this != ValidationResult.undefined() && !this.isValid;
  }

  @Override
  public String toString() {
    return "ValidationResulut:" + this.isValid + "(" + this.message + ")";
  }

}
