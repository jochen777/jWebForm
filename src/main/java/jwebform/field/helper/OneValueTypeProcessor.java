package jwebform.field.helper;

import jwebform.field.structure.FieldResult;
import jwebform.field.structure.HTMLProducer;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;

import java.util.function.Predicate;

/**
 * Generic class that helps with processing and rendering of standard single types
  */
public class OneValueTypeProcessor {

  final public String name;
  final public String initialValue;
  final private Predicate<String> alwaysFine = (t) -> true;


  public OneValueTypeProcessor(String name, String initialValue) {
    this.name = name;
    this.initialValue = initialValue;
  }

  public FieldResult calculateTypeWithInputCheck(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer, Predicate<String> validateInput) {
    return calculateTypeWithInputCheck(env, htmlProducer, validateInput, 1);
  }

  public FieldResult calculateTypeWithInputCheck(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer, Predicate<String> validateInput, int tabIndexIncr) {

    String requestVal = env.getEnv().getParameter(name);
    String value = "";
    String input = fetchValue(env, requestVal, initialValue);
    if (validateInput.test(input)) {
      value = input;
    }
    return FieldResult.builder().withValue(value)
        .withStaticFieldInfo(new StaticFieldInfo(name, htmlProducer, tabIndexIncr)).build();
  }

  public FieldResult calculateFieldResult(EnvWithSubmitInfo env, HTMLProducer htmlProducer) {
    return calculateTypeWithInputCheck(env, htmlProducer, alwaysFine);
  }

  public FieldResult calculateFieldResultNoTabIndexIncrement(EnvWithSubmitInfo env,
      HTMLProducer htmlProducer) {
    return calculateTypeWithInputCheck(env, htmlProducer, alwaysFine, 0);
  }


  public ValidationResult validate(EnvWithSubmitInfo env, Validator validator,
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
