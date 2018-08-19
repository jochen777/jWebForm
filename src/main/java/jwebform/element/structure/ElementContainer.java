package jwebform.element.structure;

import jwebform.validation.Validator;

// holds everything, that is important to an element (Element Definition, validation, behaviours)
public class ElementContainer {
  public final FieldType element;
  public final Validator validator;
  public final Decoration decoration;

  private final static Decoration emptyDecoration = new Decoration("");

  public ElementContainer(FieldType element, Validator validator,
      Decoration decoration) {
    this.element = element;
    this.validator = validator;
    this.decoration = decoration;
  }



  public ElementContainer(FieldType element, Validator validator) {
    this(element, validator, emptyDecoration);
  }


  public ElementContainer(FieldType element) {
    this(element, Validator.emptyValidator()); 
  }


  @Override
  public String toString() {
    return String.format("ElementContainer: %s - %s ", element, validator);
  }

}
