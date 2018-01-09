package jwebform.element.structure;

import java.util.function.Function;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

// Every jWebForm Element must implement this interface. It is just the "apply" fucntion of the
// "Function" (java.util.function.Function)
@FunctionalInterface
public interface Element extends Function<EnvWithSubmitInfo, ElementResult> {

  default public ElementContainer of() {
    return new ElementContainer(this);
  }

  default public ElementContainer of(Validator validator) {
    return new ElementContainer(this, validator);
  }

}
