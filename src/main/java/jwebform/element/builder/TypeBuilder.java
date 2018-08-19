package jwebform.element.builder;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.FieldType;
import jwebform.element.structure.ElementContainer;
import jwebform.validation.Criterion;
import jwebform.validation.Validator;

import java.util.function.Supplier;

public class TypeBuilder {

  private String label = "";
  private String helptext = "";
  private String placeholder = "";
  private Criterion[] criteria = new Criterion[0];

  private Supplier<FieldType> typeSupplier;


  public TypeBuilder withTypeSupplier(Supplier<FieldType> typeSupplier) {
    this.typeSupplier = typeSupplier;
    return this;
  }

  public TypeBuilder withLabel(String label) {
    this.label = label;
    return this;
  }

  public TypeBuilder withHelptext(String helptext) {
    this.helptext = helptext;
    return this;
  }

  public TypeBuilder withPlaceholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }


  public TypeBuilder withCriteria(Criterion... criteria) {
    this.criteria = criteria;
    return this;
  }


  public ElementContainer build() {
    return new ElementContainer(typeSupplier.get(), new Validator(criteria),
        new Decoration(label, helptext, placeholder));
  }



}
