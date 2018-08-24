package jwebform.field;

import jwebform.field.helper.OneValueElementProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements SingleFieldType {

  public final OneValueElementProcessor oneValueElement;

  public PasswordType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- password -->");
  }



  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
