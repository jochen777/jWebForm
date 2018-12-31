package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;

public class HiddenType implements SingleFieldType {

  public final String name;
  public final String value;
  public final OneValueTypeProcessor oneValueType;

  public HiddenType(String name, String initialValue) {
    this.name = name;
    this.value = initialValue;
    this.oneValueType = new OneValueTypeProcessor(name, initialValue);

  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueType.calculateFieldResultNoTabIndexIncrement(env, t -> "<!-- hidden -->");
  }

}
