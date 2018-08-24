package jwebform.field;

import jwebform.field.helper.OneValueElementProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements SingleFieldType {

  final public OneValueElementProcessor oneValueElement;

  public TextType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
  }

  public TextType(String name, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- text -->");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
