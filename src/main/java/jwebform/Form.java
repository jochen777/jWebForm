package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.env.Env;
import jwebform.processors.Processor;
import jwebform.validation.FormValidator;

// Represents a form
public final class Form {

  private final List<ElementContainer> elements;
  private final String id;
  private final List<FormValidator> formValidators;
  private final Processor processor;

  // Constructors

  // full
  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators,
      Processor processor) {
    this.elements = elements;
    this.id = id;
    this.formValidators = formValidators;
    this.processor = processor;
  }

  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
    this(id, elements, formValidators, new Processor());
  }

  public Form(String id, List<ElementContainer> elements) {
    this(id, elements, new ArrayList<>());
  }


  public Form(String id, ElementContainer... elements) {
    this(id, new ArrayList<>(), elements);
  }

  public Form(String id, Element... elements) {
    this(id, packElementsInContainer(elements), new ArrayList<>());
  }


  public Form(String id, List<FormValidator> formValidators, ElementContainer... elements) {
    this(id, Arrays.asList(elements), formValidators);
  }

  public Form(String id, List<FormValidator> formValidators, Element... elements) {
    this(id, packElementsInContainer(elements), formValidators);
  }
  // End constructors

  // process each element, run validations
  public final FormResult run(Env env) {
    return processor.run(env, id, elements, formValidators);
  }



  private static List<ElementContainer> packElementsInContainer(Element... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (int i = 0; i < elements.length; i++) {
      ec.add(new ElementContainer(elements[i]));
    }
    return ec;
  }


  public final List<ElementContainer> getElements() {
    return elements;
  }

  public final String getId() {
    return id;
  }

 

}
