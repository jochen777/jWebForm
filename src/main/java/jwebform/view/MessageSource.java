package jwebform.view;

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

  String getMessage(String key);

  default String getSafeMessage(String key) {
    try {
      return this.getMessage(key);
    } catch (Exception e) {
      return "??" + key + "??";
    }
  }


  default String getMessage(ValidationResult vr) {
    // give translated messages higher prio than message-keys
    if (vr.getTranslatedMessage() != null) {
      return vr.getTranslatedMessage();
    } else if (vr.getMessage() != null) {
      return (String.format(this.getSafeMessage(vr.getMessage()), vr.getErrorVals()));

    } else {
      throw new IllegalArgumentException(
          "ValidationResult has neither message-key nor translated text");
    }
  }

}
