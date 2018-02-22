package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jwebform.element.SimpleGroup;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupElement;
import jwebform.env.Env;
import jwebform.processors.FormResultBuilder;
import jwebform.processors.Processor;
import jwebform.validation.FormValidator;

// Represents a form
public final class Form {

  private final String id;
  private final FormResultBuilder formResultBuilder;

  private final GroupElement group;

  // Constructors

  // full
  public Form(String id, GroupElement group, FormResultBuilder formResultBuilder) {
    this.id = id;
    this.formResultBuilder = formResultBuilder;
    this.group = group;
  }


  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators,
      FormResultBuilder formResultBuilder) {
    this(id, new SimpleGroup(elements, formValidators), formResultBuilder);
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
    Processor p = new Processor();
    Map<ElementContainer, ElementResult> result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result));
  }



  private static List<ElementContainer> packElementsInContainer(Element... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (int i = 0; i < elements.length; i++) {
      ec.add(new ElementContainer(elements[i]));
    }
    return ec;
  }


  public final List<ElementContainer> getElements() {
    return group.getChilds();
  }

  public final String getId() {
    return id;
  }

 

}
