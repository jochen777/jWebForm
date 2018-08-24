package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements SingleFieldType {

  public final OneValueTypeProcessor oneValueElement;

  public PasswordType(String name) {
    this.oneValueElement = new OneValueTypeProcessor(name, "");
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateFieldResult(env, t -> "<!-- password -->");
  }



  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
