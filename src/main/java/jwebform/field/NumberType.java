package jwebform.field;

import java.util.Optional;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

public class NumberType implements SingleFieldType {

  private final int initialNumber;

  public final OneValueTypeProcessor oneValueType;

  public NumberType(String name, int initialValue) {
    this.oneValueType = new OneValueTypeProcessor(name, Integer.toString(initialValue));
    initialNumber = initialValue;
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    String requestVal = env.getEnv().getRequest().getParameter(oneValueType.name);
    int parsedNumber = 0;
    String parsedNumberVal = "";
    ValidationResult vr = ValidationResult.undefined();
    Optional<Integer> numbOptional;

    if ("".equals(requestVal)) {
      numbOptional = Optional.empty();
    } else {
      String val = env.isSubmitted() ? requestVal : Integer.toString(initialNumber);
      try {
        parsedNumber = Integer.parseInt(val);
        parsedNumberVal = Integer.toString(parsedNumber);
        numbOptional = Optional.of(parsedNumber);
      } catch (NumberFormatException e) {
        vr = ValidationResult.fail("jformchecker.not_a_number");
        parsedNumber = 0;
        numbOptional = Optional.empty();
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
