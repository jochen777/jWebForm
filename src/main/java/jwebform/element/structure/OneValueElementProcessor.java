package jwebform.element.structure;

import java.util.function.Predicate;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

// Generic class that deals with processing and rendering of standard one field elements
public class OneValueElementProcessor {

  final public String name;
  final public String initialValue;
  final public Validator validator;
  final public OneFieldDecoration decoration;


  public OneValueElementProcessor(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;
  }

  public ElementResult calculateElementResult(
      EnvWithSubmitInfo env,
      String key,
      HTMLProducer htmlProducer,
      Element source,
      Predicate<String> validateInput) {

    String requestVal = env.getEnv().getRequest().getParameter(name);
    String value = "";
    String input = fetchValue(env, requestVal, initialValue);
    if (validateInput.test(input)) {
      value = input;
    }
    ValidationResult vr = validate(env, validator, requestVal, value);
    return new ElementResult(vr, value, new StaticElementInfo(name, htmlProducer, 1, key), source);
  }


  public ValidationResult validate(
      EnvWithSubmitInfo env,
      Validator validator,
      String requestVal,
      String value) {
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
