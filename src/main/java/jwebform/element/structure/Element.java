package jwebform.element.structure;

import jwebform.validation.Validator;

// just a marker interface. A form has elements
public interface Element {

  default public ElementContainer of() {
    return new ElementContainer(this);
  }


  default public ElementContainer of(Validator validator) {
    return new ElementContainer(this, validator);
  }
}
