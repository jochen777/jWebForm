package jwebform.field.structure;

import jwebform.validation.Criterion;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

// just a marker interface. A form has fields
public interface FieldType {

  default Field of() {
    return new Field(this);
  }


  default Field of(Criterion... criterion) {
    return new Field(this, criterion);
  }

  default Field of(Decoration decoration, Criterion... criterion) {
    return new Field(this,  decoration, criterion);
  }

  default Field of(Decoration decoration) {
    return new Field(this, decoration);
  }

}
