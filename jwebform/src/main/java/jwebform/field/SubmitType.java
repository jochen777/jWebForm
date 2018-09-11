package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;

public class SubmitType implements SingleFieldType {

  public final String label;
  public final String name;

  public SubmitType(String label) {
    this(label, "submit");
  }

  public SubmitType(String label, String name) {
    this.label = label;
    this.name = name;
  }


  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return FieldResult.builder()
        .withStaticFieldInfo(new StaticFieldInfo(name, t -> "<!-- submit -->", 1)).build();
  }

}
