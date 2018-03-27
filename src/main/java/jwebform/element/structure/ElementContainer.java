package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.behaviour.Behaviour;
import jwebform.validation.Validator;

// holds everything, that is important to an element (Element Definition, validation, behaviours)
public class ElementContainer {
  public final Element element;
  public final Validator validator;
  public final List<Behaviour> behaviour;
  public final Decoration decoration;

  private final static List<Behaviour> emptyList = new ArrayList<>();
  private final static Decoration emptyDecoration = new Decoration("");

  public ElementContainer(Element element, Validator validator, Behaviour behaviour,
      Decoration decoration) {
    this.element = element;
    this.validator = validator;
    this.behaviour = buildListFromOneBehaviour(behaviour);
    this.decoration = decoration;
  }

  public ElementContainer(Element element, Validator validator, List<Behaviour> behaviour,
      Decoration decoration) {
    this.element = element;
    this.validator = validator;
    this.behaviour = behaviour;
    this.decoration = decoration;
  }


  public ElementContainer(Element element, Validator validator) {
    this(element, validator, emptyList, emptyDecoration);
  }

  public ElementContainer(Element element, Validator validator, Decoration decoration) {
    this(element, validator, emptyList, decoration);
  }

  public ElementContainer(Element element) {
    this(element, Validator.emptyValidator()); 
  }

  private List<Behaviour> buildListFromOneBehaviour(Behaviour behaviour2) {
    List<Behaviour> behaviourList = new ArrayList<>();
    behaviourList.add(behaviour2);
    return behaviourList;
  }


  @Override
  public String toString() {
    return String.format("ElementContainer: %s - %s - %s", element, validator, behaviour);
  }

}
