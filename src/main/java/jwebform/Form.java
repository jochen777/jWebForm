package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jwebform.element.SimpleGroup;
import jwebform.element.builder.TypeBuilder;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.element.structure.SingleType;
import jwebform.env.Env;
import jwebform.processors.FormResultBuilder;
import jwebform.processors.Processor;
import jwebform.validation.FormValidator;

// Represents a form
public final class Form {

  private final String id;
  private final FormResultBuilder formResultBuilder;

  private final GroupType group;

  // Constructors

  // full
  public Form(String id, GroupType group, FormResultBuilder formResultBuilder) {
    this.id = id;
    this.formResultBuilder = formResultBuilder;
    this.group = group;
  }

  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators,
      FormResultBuilder formResultBuilder) {
    this(id, new SimpleGroup(elements, formValidators), formResultBuilder);
  }

  public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
    this(id, elements, formValidators, FormResult::new);
  }

  public Form(String id, List<ElementContainer> elements) {
    this(id, elements, new ArrayList<>());
  }


  public Form(String id, ElementContainer... elements) {
    this(id, new ArrayList<>(), elements);
  }

  public Form(String id, TypeBuilder... elements) {
    this(id, new ArrayList<>(), buildElementContainers(elements));
  }

  public Form(String id, List<FormValidator> formValidators, TypeBuilder... elements) {
    this(id, formValidators, buildElementContainers(elements));
  }



  private static ElementContainer[] buildElementContainers(TypeBuilder[] elements) {
    ElementContainer[] elList = new ElementContainer[elements.length];
    for (int i = 0; i < elements.length; i++) {
      elList[i] = elements[i].build();
    }
    return elList;
  }

  public Form(ElementContainer... elements) {
    this("id", new ArrayList<>(), elements);
  }


  public Form(String id, SingleType... elements) {
    this(id, packElementsInContainer(elements), new ArrayList<>());
  }


  public Form(String id, List<FormValidator> formValidators, ElementContainer... elements) {
    this(id, Arrays.asList(elements), formValidators);
  }

  public Form(String id, List<FormValidator> formValidators, SingleType... elements) {
    this(id, packElementsInContainer(elements), formValidators);
  }
  // End constructors

  // process each element, run validations
  public final FormResult run(Env env) {
    Processor p = new Processor();
    Map<ElementContainer, ElementResult> result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result));
  }



  private static List<ElementContainer> packElementsInContainer(SingleType... elements) {
    List<ElementContainer> ec = new ArrayList<>();
    for (SingleType element : elements) {
      ec.add(new ElementContainer(element));
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
