package jwebform.element.structure;

import jwebform.validation.Validator;

// holds everything, that is important to an element
public class ElementContainer {
  public final Element element;
  public final Validator validator;
  public final Behaviour behaviour;

  public ElementContainer(Element element, Validator validator, Behaviour behaviour) {
    this.element = element;
    this.validator = validator;
    this.behaviour = behaviour;
  }

  public ElementContainer(Element element) {
    this(element, null, null); // RFE: Do we need nulls, can we work with statics?!?
  }

}
