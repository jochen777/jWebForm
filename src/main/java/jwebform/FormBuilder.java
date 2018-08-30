package jwebform;

import jwebform.field.SimpleGroup;
import jwebform.field.builder.FieldBuilder;
import jwebform.field.structure.Field;
import jwebform.field.structure.GroupFieldType;
import jwebform.field.structure.SingleFieldType;
import jwebform.processor.FormResultBuilder;
import jwebform.validation.FormValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// Simplifies Building of a form.
public class FormBuilder {

  private String id = "id"; // Default
  private FormResultBuilder formResultBuilder;
  private GroupFieldType group = new SimpleGroup(new ArrayList<>(), new ArrayList<>());
  private List<Field> fields = new ArrayList<>();
  private List<FormValidator> formValidators = new ArrayList<>();
  private List<FieldBuilder> typeBuilders = new ArrayList<>();
  private List<SingleFieldType> singleTypes = new ArrayList<>();


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

  private GroupFieldType buildGroup() {
    fields.addAll(processTypeBuilders());
    fields.addAll(processSingleTypes());
    group.getChilds().addAll(fields);
    group.getValidators(null /* will be ignored */).addAll(formValidators);
    return group;
  }


  private Collection<Field> processSingleTypes() {
    List<Field> ec = new ArrayList<>();
    for (SingleFieldType type : singleTypes) {
      ec.add(new Field(type));
    }
    return ec;
  }


  private Collection<Field> processTypeBuilders() {
    List<Field> elList = new ArrayList<>();
    for (FieldBuilder typeBuilder : typeBuilders) {
      elList.add(typeBuilder.build());
    }
    return elList;
  }


  public FormBuilder group(GroupFieldType group) {
    this.group = group;
    return this;
  }

  public FormBuilder fields(List<Field> elList) {
    fields.addAll(elList);
    return this;
  }

  public FormBuilder fields(Field... elList) {
    fields.addAll(new ArrayList<>(Arrays.asList(elList)));
    return this;
  }


  public FormBuilder typeBuilder(List<FieldBuilder> typeBuilders) {
    this.typeBuilders.addAll(typeBuilders);
    return this;
  }

  public FormBuilder typeBuilder(FieldBuilder... typeBuilder) {
    this.typeBuilders.addAll(new ArrayList<>(Arrays.asList(typeBuilder)));
    return this;
  }

  public FormBuilder singleTypes(List<SingleFieldType> singleTypeList) {
    this.singleTypes.addAll(singleTypeList);
    return this;
  }

  public FormBuilder singleTypes(SingleFieldType... singleType) {
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

  // helps to build easyly an array
//  public
//  static <T> T[] array(T... values) { return values; }


}
