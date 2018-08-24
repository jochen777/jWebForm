package jwebform.field;

import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SubmitType implements SingleFieldType {

  // TODO: Remove label. Should be taken from decoration
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
        .withStaticElementInfo(new StaticFieldInfo(name, t -> "<!-- submit -->", 1)).build();
  }

}
