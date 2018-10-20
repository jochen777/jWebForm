package jwebform.integration;

import java.util.ArrayList;
import java.util.List;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.Validator;

// convenience methods for building the form
public class FormBuilder {

  String id = "id";
  List<Field> elements = new ArrayList<>();
  List<FormValidator> formValidators = new ArrayList<>();

  public FormBuilder() {

  }

  public FormBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public FormBuilder addElement(FieldType type, Criterion... inputCriterium) {
    elements.add(type.of(inputCriterium));
    return this;
  }


  public Form build() {
    return jwebform.FormBuilder
        .flexible(id,
            (formId, elementResults, formIsValid) -> new FormResult(formId, elementResults,
                formIsValid))
        .fields(elements).validation(formValidators).build();

  }

}
