package jwebform.field.structure;

import jwebform.validation.Criterion;
import jwebform.validation.Validator;

// holds everything, that is important to an fieldType (Element Definition, validation, behaviours)
public class Field {
  public final FieldType fieldType;
  public final Criterion [] criteria;
  public final Decoration decoration;

  private final static Decoration emptyDecoration = new Decoration("");

  public Field(FieldType fieldType, Decoration decoration, Criterion ... criteria) {
    this.fieldType = fieldType;
    this.criteria = criteria;
    this.decoration = decoration;
  }



  public Field(FieldType element, Criterion ... criteria) {
    this(element, emptyDecoration, criteria);
  }


  public Field(FieldType element) {
    this(element, emptyDecoration, new Criterion[0]);
  }


  // RFE: This must be injected! Validator could change - for example with Beanvalidator
  public Validator getValidator() {
    return new Validator(criteria);
  }

  @Override
  public String toString() {
    return String.format("Field: %s - %s ", fieldType, criteria);
  }

}
