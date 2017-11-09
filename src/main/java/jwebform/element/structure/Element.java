package jwebform.element.structure;

import java.util.function.Function;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

@FunctionalInterface
public interface Element extends Function<EnvWithSubmitInfo, ElementResult> {

  default public ElementContainer of() {
    return new ElementContainer(this);
  }

  default public ElementContainer of(Validator validator) {
    return new ElementContainer(this, validator);
  }

}
