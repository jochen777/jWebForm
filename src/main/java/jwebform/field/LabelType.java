package jwebform.field;

import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;

// RFE: Is this useful at all? (as long as we have HTMLType)
public class LabelType implements SingleFieldType {

  public final String label;

  // TODO: Label sollte in Decoration
  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return FieldResult.builder()
        .withStaticElementInfo(new StaticFieldInfo("", t -> "<!-- label -->", 0)).withValidationResult(ValidationResult
        .ok()).build();
  }


  public String getLabel() {
    return label;
  }

}
