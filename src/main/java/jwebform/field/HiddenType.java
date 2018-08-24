package jwebform.field;

import jwebform.field.helper.OneValueElementProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HiddenType implements SingleFieldType {

  public final String name;
  public final String value;
  final public OneValueElementProcessor oneValueElement;

  public HiddenType(String name, String initialValue) {
    this.name = name;
    this.value = initialValue;
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);

  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResultNoTabIndexIncrement(env, t -> "<!-- hidden -->");
  }

}
