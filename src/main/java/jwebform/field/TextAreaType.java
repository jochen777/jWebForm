package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextAreaType implements SingleFieldType {

  public final OneValueTypeProcessor oneValueType;

  public TextAreaType(String name, String initialValue) {
    oneValueType = new OneValueTypeProcessor(name, initialValue);

  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueType.calculateFieldResult(env, t -> "<!-- textarea -->");
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueType.name);
  }

}
