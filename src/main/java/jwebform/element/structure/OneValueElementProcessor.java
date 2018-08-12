package jwebform.element.structure;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

import java.util.function.Predicate;

// Generic class that deals with processing and rendering of standard one field elements
public class OneValueElementProcessor {

  final public String name;
  final public String initialValue;
  final public Predicate<String> alwaysFine = (t) -> true;


  public OneValueElementProcessor(String name, String initialValue) {
    this.name = name;
    this.initialValue = initialValue;
  }

  public ElementResult calculateElementResultWithInputCheck(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer, Predicate<String> validateInput) {
    return calculateElementResultWithInputCheck(env, htmlProducer, validateInput, 1);
  }

  public ElementResult calculateElementResultWithInputCheck(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer, Predicate<String> validateInput, int tabIndexIncr) {

    String requestVal = env.getEnv().getRequest().getParameter(name);
    String value = "";
    String input = fetchValue(env, requestVal, initialValue);
    if (validateInput.test(input)) {
      value = input;
    }
    // ValidationResult vr = validate(env, validator, requestVal, value);
    return ElementResult.builder().withValue(value)
        .withStaticElementInfo(new StaticElementInfo(name, htmlProducer, tabIndexIncr)).build();
  }

  public ElementResult calculateElementResult(EnvWithSubmitInfo env, HTMLProducer htmlProducer) {
    return calculateElementResultWithInputCheck(env, htmlProducer, alwaysFine);
  }

  public ElementResult calculateElementResultNoTabIndexIncrement(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer) {
    return calculateElementResultWithInputCheck(env, htmlProducer, alwaysFine, 0);
  }


  public ValidationResult validate(EnvWithSubmitInfo env, Validator validator, String requestVal,
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
