package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

public class LabelType implements SingleFieldType {

  public final String label;

  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return FieldResult.builder()
        .withStaticFieldInfo(new StaticFieldInfo("", t -> "<!-- label -->", 0))
        .withValidationResult(ValidationResult.ok()).build();
  }


  public String getLabel() {
    return label;
  }

}
