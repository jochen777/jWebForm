package jwebform.integration;

import jwebform.validation.ValidationResult;

/**
 * Source for mssages that should be displayed typically coming from a property file holding
 * localized messages
 * 
 * @author jpier
 *
 */
@FunctionalInterface
public interface MessageSource {

  public String getMessage(String key);

  public default String getSafeMessage(String key) {
    try {
      String msg = this.getMessage(key);
      return msg;
    } catch (Exception e) {
      return "??" + key + "??";
    }
  }


  public default String getMessage(ValidationResult vr) {
    // give translated messages higher prio than message-keys
    if (vr.getTranslatedMessage() != null) {
      return vr.getTranslatedMessage();
    } else if (vr.getMessageKey() != null) {
      return (String.format(this.getSafeMessage(vr.getMessageKey()), vr.getErrorVals()));
    } else {
      throw new IllegalArgumentException(
          "ValidationResult has neither message-key nor translated text");
    }
  }

}
