package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements SingleFieldType {

  public final OneValueTypeProcessor oneValueField;

  public TextType(String name) {
    this.oneValueField = new OneValueTypeProcessor(name, "");
  }

  public TextType(String name, String initialValue) {
    this.oneValueField = new OneValueTypeProcessor(name, initialValue);
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueField.calculateFieldResult(env, t -> "<!-- text -->");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueField.name);
  }

}
