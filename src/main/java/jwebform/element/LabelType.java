package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;

// RFE: Is this useful at all? (as long as we have HTMLType)
public class LabelType implements SingleType {

  public final String label;

  // TODO: Label sollte in Decoration
  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return ElementResult.builder()
        .withStaticElementInfo(new StaticElementInfo("", t -> "<!-- label -->", 0)).withValidationResult(ValidationResult
        .ok()).build();
  }


  public String getLabel() {
    return label;
  }

}
