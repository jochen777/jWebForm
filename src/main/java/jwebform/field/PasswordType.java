package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements SingleFieldType {

  public final OneValueTypeProcessor oneValueType;

  public PasswordType(String name) {
    this.oneValueType = new OneValueTypeProcessor(name, "");
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueType.calculateFieldResult(env, t -> "<!-- password -->");
  }



  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueType.name);
  }

}
