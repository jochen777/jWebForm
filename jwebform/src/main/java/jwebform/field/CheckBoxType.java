package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;

public class CheckBoxType implements SingleFieldType {

  private final String name;

  private final boolean initialValue;


  public CheckBoxType(String name, boolean initialValue) {
    this.name = name;
    this.initialValue = initialValue;
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    // somewhat ugly, but checkboxes ARE ugly
    String requestVal = env.getEnv().getParameter(name);
    String value = "true";
    boolean checked;
    if (!env.isSubmitted()) {
      value = "" + initialValue;
      checked = initialValue;
    } else {
      checked = true;
      if ("".equals(requestVal) || requestVal == null) {
        value = "";
        checked = false;
      }
    }
    return FieldResult.builder().withValue(value)
        .withStaticFieldInfo(new StaticFieldInfo(name, t -> "<!-- checkbox -->", 1))
        .withValueObject(checked).build();
  }


  @Override
  public String toString() {
    return String.format("CheckBoxInput. name=%s", name);
  }

}
