package jwebform.element.structure;

import java.util.function.Predicate;

import jwebform.env.Env;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

public class OneValueElementProcessor {

  public ElementResult calculateElementResult(
      Env env,
      String name,
      String initialValue,
      Validator validator,
      StaticElementInfo staticElementInfo,
      Element source,
      Predicate<String> validateInput) {
    String requestVal = env.getRequest().getParameter(name);
    String value = "";
    String input = fetchValue(requestVal, initialValue);;
    if (validateInput.test(input)) {
      value = input;
    }
    ValidationResult vr = validate(validator, requestVal, value);
    return new ElementResult(vr, value, staticElementInfo, source);
  }


  private boolean formSubmitted(String requestVal) {
    return requestVal != null;
  }

  private ValidationResult validate(Validator validator, String requestVal, String value) {
    if (formSubmitted(requestVal)) {
      return validator.validate(value);
    }
    return ValidationResult.undefined();
  }

  private String fetchValue(String requestVal, String initialValue) {
    if (formSubmitted(requestVal)) {
      return requestVal;
    }
    return initialValue;
  }



}
