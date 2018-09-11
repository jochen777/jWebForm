package jwebform.field.structure;

import jwebform.validation.Criterion;
import jwebform.validation.Validator;

/**
 * holds everything, that is important to a field of a form (Type, validation criteria, decoration)
 */
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

  public Field(FieldType fieldType, Criterion ... criteria) {
    this(fieldType, emptyDecoration, criteria);
  }


  public Field(FieldType fieldType) {
    this(fieldType, emptyDecoration, new Criterion[0]);
  }

  @Override
  public String toString() {
    return String.format("Field: %s - %s ", fieldType, criteria);
  }

}
