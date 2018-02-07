package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.env.Env;
import jwebform.processors.FormResultBuilder;
import jwebform.processors.Processor;
import jwebform.validation.FormValidator;

// Represents a form
public final class Form {

  private final List<ElementContainer> elements;
  private final String id;
  private final List<FormValidator> formValidators;
  private final FormResultBuilder formResultBuilder;

  // Constructors

  // full
  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators,
      FormResultBuilder formResultBuilder) {
    this.elements = elements;
    this.id = id;
    this.formValidators = formValidators;
    this.formResultBuilder = formResultBuilder;
  }

  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
    this(id, elements, formValidators,
        (formId, elementResults, formIsValid) -> new FormResult(formId, elementResults,
            formIsValid));
  }

  public Form(String id, List<ElementContainer> elements) {
    this(id, elements, new ArrayList<>());
  }


  public Form(String id, ElementContainer... elements) {
    this(id, new ArrayList<>(), elements);
  }

  public Form(ElementContainer... elements) {
    this("id", new ArrayList<>(), elements);
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
    return new Processor().run(env, id, elements, formValidators, formResultBuilder);
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
