package jwebform.element.structure;

import jwebform.validation.Validator;

// just a marker interface. A form has elements
public interface Element {

  default ElementContainer of() {
    return new ElementContainer(this);
  }


  default ElementContainer of(Validator validator) {
    return new ElementContainer(this, validator);
  }

  default ElementContainer of(Validator validator, Decoration decoration) {
    return new ElementContainer(this, validator, decoration);
  }

  default ElementContainer of(Decoration decoration) {
    return new ElementContainer(this, Validator.emptyValidator(), decoration);
  }

}
