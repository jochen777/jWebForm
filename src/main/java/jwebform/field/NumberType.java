package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.env.Env.EnvWithSubmitInfo;

public class NumberType implements SingleFieldType {

  private final int initialNumber;

  public final OneValueTypeProcessor oneValueType;

  // RFE: Maybe we need here an Optional<Integer> to allow nothing on the first place.
  public NumberType(String name, int initialValue) {
    this.oneValueType = new OneValueTypeProcessor(name, Integer.toString(initialValue));
    initialNumber = initialValue;
  }

  @Override
  // TODO: Test this!
  public FieldResult apply(EnvWithSubmitInfo env) {
    String requestVal = env.getEnv().getRequest().getParameter(oneValueType.name);
    String val = env.isSubmitted() ? requestVal : Integer.toString(initialNumber);
    int parsedNumber = 0;
    String parsedNumberVal = "";
    try {
      parsedNumber = Integer.parseInt(val);
      parsedNumberVal = Integer.toString(parsedNumber);
    } catch (NumberFormatException e) {
      parsedNumber = 0;
    }
    return FieldResult.builder().withValue(parsedNumberVal)
        .withStaticFieldInfo(
            new StaticFieldInfo(oneValueType.name, t -> "<!-- number -->", 1))
        .withValueObject(parsedNumber).build();
  }


  @Override
  public String toString() {
    return String.format("NumberInput. name=%s", oneValueType.name);
  }


}
