package jwebform.field.structure;

import jwebform.validation.Validator;

// holds everything, that is important to an element (Element Definition, validation, behaviours)
public class Field {
  public final FieldType element;
  public final Validator validator;
  public final Decoration decoration;

  private final static Decoration emptyDecoration = new Decoration("");

  public Field(FieldType element, Validator validator,
      Decoration decoration) {
    this.element = element;
    this.validator = validator;
    this.decoration = decoration;
  }



  public Field(FieldType element, Validator validator) {
    this(element, validator, emptyDecoration);
  }


  public Field(FieldType element) {
    this(element, Validator.emptyValidator()); 
  }


  @Override
  public String toString() {
    return String.format("Field: %s - %s ", element, validator);
  }

}
