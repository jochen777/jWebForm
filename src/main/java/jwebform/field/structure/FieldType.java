package jwebform.field.structure;

import jwebform.validation.Validator;

// just a marker interface. A form has fields
public interface FieldType {

  default Field of() {
    return new Field(this);
  }


  default Field of(Validator validator) {
    return new Field(this, validator);
  }

  default Field of(Validator validator, Decoration decoration) {
    return new Field(this, validator, decoration);
  }

  default Field of(Decoration decoration) {
    return new Field(this, Validator.emptyValidator(), decoration);
  }

}
