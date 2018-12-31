package jwebform.field;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

public class HtmlType implements SingleFieldType {

  public final String html;

  public HtmlType(String html) {
    this.html = html;
  }


  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    // Beware - no escaping!!
    return FieldResult.builder().withStaticFieldInfo(new StaticFieldInfo("", t -> html, 0)).
    withValidationResult(ValidationResult
      .ok()).build();
  }

}
