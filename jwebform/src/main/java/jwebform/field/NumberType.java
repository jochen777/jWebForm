package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

import java.util.Optional;

public class NumberType implements SingleFieldType {

  private final int initialNumber;

  public final OneValueTypeProcessor oneValueType;

  public NumberType(String name, int initialValue) {
    this.oneValueType = new OneValueTypeProcessor(name, Integer.toString(initialValue));
    initialNumber = initialValue;
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    String requestVal = env.getEnv().getParameter(oneValueType.name);
    ValidationResult vr = ValidationResult.undefined();

    int parsedNumber = 0;
    String parsedNumberVal = "";
    Optional<Integer> numbOptional;
    if (!env.isSubmitted()) {
      parsedNumber = initialNumber;
      numbOptional = Optional.of(initialNumber);
      parsedNumberVal = Integer.toString(parsedNumber);
    } else {
      if ("".equals(requestVal)) {
        numbOptional = Optional.empty();
      } else {
        String val = env.isSubmitted() ? requestVal : Integer.toString(initialNumber);
        try {
          parsedNumber = Integer.parseInt(val);
          parsedNumberVal = Integer.toString(parsedNumber);
          numbOptional = Optional.of(parsedNumber);
        } catch (NumberFormatException e) {
          vr = ValidationResult.fail("jwebform.not_a_number");
          numbOptional = Optional.empty();
        }
      }
    }
    return FieldResult.builder().withValue(parsedNumberVal)
        .withStaticFieldInfo(new StaticFieldInfo(oneValueType.name, t -> "<!-- number -->", 1))
        .withValidationResult(vr).withValueObject(numbOptional).build();
  }


  @Override
  public String toString() {
    return String.format("NumberInput. name=%s", oneValueType.name);
  }


}
