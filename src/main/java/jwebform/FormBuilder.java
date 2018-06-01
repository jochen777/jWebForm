package jwebform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import jwebform.element.SimpleGroup;
import jwebform.element.builder.TypeBuilder;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.GroupType;
import jwebform.element.structure.SingleType;
import jwebform.processors.FormResultBuilder;
import jwebform.validation.FormValidator;

// Simplifies Building of a form.
public class FormBuilder {

  private String id = "id"; // Default
  private FormResultBuilder formResultBuilder;
  private GroupType group = new SimpleGroup(new ArrayList<>(), new ArrayList<>());
  private List<ElementContainer> elementContainers = new ArrayList<>();
  private List<FormValidator> formValidators = new ArrayList<>();
  private List<TypeBuilder> typeBuilders = new ArrayList<>();
  private List<SingleType> singleTypes = new ArrayList<>();


  private FormBuilder(String id, FormResultBuilder formResultBuilder) {
    this.id = id;
    this.formResultBuilder = formResultBuilder;
  }


  public static FormBuilder simple() {
    return new FormBuilder("id", FormResult::new);
  }

  public static FormBuilder withId(String id) {
    return new FormBuilder(id, FormResult::new);
  }

  public static FormBuilder flexible(String id, FormResultBuilder fb) {
    return new FormBuilder(id, fb);
  }



  public Form build() {
    return new Form(id, buildGroup(), formResultBuilder);
  }

  private GroupType buildGroup() {
    elementContainers.addAll(processTypeBuilders());
    elementContainers.addAll(processSingleTypes());
    group.getChilds().addAll(elementContainers);
    group.getValidators(null /* will be ignored */).addAll(formValidators);
    return group;
  }


  private Collection<ElementContainer> processSingleTypes() {
    List<ElementContainer> ec = new ArrayList<>();
    for (SingleType element : singleTypes) {
      ec.add(new ElementContainer(element));
    }
    return ec;
  }


  private Collection<ElementContainer> processTypeBuilders() {
    List<ElementContainer> elList = new ArrayList<>();
    for (TypeBuilder typeBuilder : typeBuilders) {
      elList.add(typeBuilder.build());
    }
    return elList;
  }


  public FormBuilder group(GroupType group) {
    this.group = group;
    return this;
  }

  public FormBuilder elementContainer(List<ElementContainer> elList) {
    elementContainers.addAll(elList);
    return this;
  }

  public FormBuilder elementContainer(ElementContainer... elList) {
    elementContainers.addAll(new ArrayList<>(Arrays.asList(elList)));
    return this;
  }

  public FormBuilder typeBuilder(List<TypeBuilder> typeBuilders) {
    this.typeBuilders.addAll(typeBuilders);
    return this;
  }

  public FormBuilder typeBuilder(TypeBuilder... typeBuilder) {
    this.typeBuilders.addAll(new ArrayList<>(Arrays.asList(typeBuilder)));
    return this;
  }

  public FormBuilder singleTypes(List<SingleType> singleTypeList) {
    this.singleTypes.addAll(singleTypeList);
    return this;
  }

  public FormBuilder singleTypes(SingleType... singleType) {
    this.singleTypes.addAll(new ArrayList<>(Arrays.asList(singleType)));
    return this;
  }

  public FormBuilder validation(List<FormValidator> formValidation) {
    this.formValidators.addAll(formValidation);
    return this;
  }

  public FormBuilder validation(FormValidator... formValidation) {
    this.formValidators.addAll(new ArrayList<>(Arrays.asList(formValidation)));
    return this;
  }


  /*
   * 
   * public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators,
   * FormResultBuilder formResultBuilder) { this(id, new SimpleGroup(elements, formValidators),
   * formResultBuilder); }
   * 
   * public Form(String id, List<ElementContainer> elements, List<FormValidator> formValidators) {
   * this(id, elements, formValidators, FormResult::new); }
   * 
   * public Form(String id, List<ElementContainer> elements) { this(id, elements, new
   * ArrayList<>()); }
   * 
   * 
   * public Form(String id, ElementContainer... elements) { this(id, new ArrayList<>(), elements); }
   * 
   * public Form(String id, TypeBuilder... elements) { this(id, new ArrayList<>(),
   * buildElementContainers(elements)); }
   * 
   * public Form(String id, List<FormValidator> formValidators, TypeBuilder... elements) { this(id,
   * formValidators, buildElementContainers(elements)); }
   * 
   * public Form(ElementContainer... elements) { this("id", new ArrayList<>(), elements); }
   * 
   * 
   * public Form(String id, SingleType... elements) { this(id, packElementsInContainer(elements),
   * new ArrayList<>()); }
   * 
   * 
   * public Form(String id, List<FormValidator> formValidators, ElementContainer... elements) {
   * this(id, Arrays.asList(elements), formValidators); }
   * 
   * public Form(String id, List<FormValidator> formValidators, SingleType... elements) { this(id,
   * packElementsInContainer(elements), formValidators); }
   * 
   */
}
