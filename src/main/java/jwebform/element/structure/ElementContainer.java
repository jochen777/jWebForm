package jwebform.element.structure;

import jwebform.validation.Validator;

// holds everything, that is important to an element (Element Definition, validation, behaviours)
public class ElementContainer {
  public final Element element;
  public final Validator validator;
  public final Decoration decoration;

  private final static Decoration emptyDecoration = new Decoration("");

  public ElementContainer(Element element, Validator validator,
      Decoration decoration) {
    this.element = element;
    this.validator = validator;
    this.decoration = decoration;
  }



  public ElementContainer(Element element, Validator validator) {
    this(element, validator, emptyDecoration);
  }


  public ElementContainer(Element element) {
    this(element, Validator.emptyValidator()); 
  }


  @Override
  public String toString() {
    return String.format("ElementContainer: %s - %s ", element, validator);
  }

}
