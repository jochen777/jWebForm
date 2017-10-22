package jwebform.element.structure;

import java.util.function.Predicate;

import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

public class OneValueElementProcessor {

  public ElementResult calculateElementResult(
      EnvWithSubmitInfo env,
      String name,
      String initialValue,
      Validator validator,
      StaticElementInfo staticElementInfo,
      Element source,
      Predicate<String> validateInput) {
	  
    String requestVal = env.getEnv().getRequest().getParameter(name);
    String value = "";
    String input = fetchValue(env, requestVal, initialValue);;
    if (validateInput.test(input)) {
      value = input;
    }
    ValidationResult vr = validate(env, validator, requestVal, value);
    return new ElementResult(vr, value, staticElementInfo, source);
  }


  private ValidationResult validate(EnvWithSubmitInfo env, Validator validator, String requestVal, String value) {
    if (env.isSubmitted()) {
      return validator.validate(value);
    }
    return ValidationResult.undefined();
  }

  private String fetchValue(EnvWithSubmitInfo env, String requestVal, String initialValue) {
    if (env.isSubmitted()) {
      return requestVal;
    }
    return initialValue;
  }



}
