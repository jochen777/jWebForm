package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

// Just for demonstration!
public class SimpleType implements SingleFieldType {

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return FieldResult.builder()
        .withStaticFieldInfo(new StaticFieldInfo("", t -> "simple\n", 0)).withValidationResult(ValidationResult
        .ok()).build();
  }

}
