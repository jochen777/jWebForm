package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;

// Just for demonstration!
public class SimpleType implements SingleType {

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return ElementResult.builder()
        .withStaticElementInfo(new StaticElementInfo("", t -> "simple\n", 0)).withValidationResult(ValidationResult
        .ok()).build();
  }

}
