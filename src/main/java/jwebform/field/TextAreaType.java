package jwebform.field;

import jwebform.field.helper.OneValueElementProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextAreaType implements SingleFieldType {

  public final OneValueElementProcessor oneValueElement;

  public TextAreaType(String name, String initialValue) {
    oneValueElement = new OneValueElementProcessor(name, initialValue);

  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- textarea -->");
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueElement.name);
  }

}
