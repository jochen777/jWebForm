package jwebform.integration.beanvalidation;

public class ExternalValidation {

  public String fieldName;
  public String validationMessage;

  public ExternalValidation(String fieldName, String validationMessage) {
    this.fieldName = fieldName;
    this.validationMessage = validationMessage;
  }


}
