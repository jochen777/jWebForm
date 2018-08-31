package jwebform.field.builder;

import jwebform.field.structure.Decoration;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.validation.Criterion;

import java.util.function.Supplier;


/**
 * A field Builder, that allows to set the Decoration and the criteria
  */
public class FieldBuilder {

  private String label = "";
  private String helptext = "";
  private String placeholder = "";
  private Criterion[] criteria = new Criterion[0];

  private Supplier<FieldType> typeSupplier;


  FieldBuilder withTypeSupplier(Supplier<FieldType> typeSupplier) {
    this.typeSupplier = typeSupplier;
    return this;
  }

  public FieldBuilder label(String label) {
    this.label = label;
    return this;
  }

  public FieldBuilder helpText(String helptext) {
    this.helptext = helptext;
    return this;
  }

  public FieldBuilder placeholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }


  public FieldBuilder criteria(Criterion... criteria) {
    this.criteria = criteria;
    return this;
  }


  public Field build() {
    return new Field(typeSupplier.get(),
        new Decoration(label, helptext, placeholder), criteria);
  }



}
