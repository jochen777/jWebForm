package jwebform.integration;

import jwebform.Form;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;

import java.util.ArrayList;
import java.util.List;

// convenience methods for building the form
public class SimpleFormBuilder {

  String id = "id";
  List<Field> elements = new ArrayList<>();
  List<FormValidator> formValidators = new ArrayList<>();

  public SimpleFormBuilder() {

  }

  public SimpleFormBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public SimpleFormBuilder addElement(FieldType type, Criterion... inputCriterium) {
    elements.add(type.of(inputCriterium));
    return this;
  }


  public Form build() {
    return jwebform.FormBuilder
        .withId(id)
        .fields(elements).validation(formValidators).build();

  }

}
