package jwebform.element.builder;

import java.util.function.Supplier;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.validation.Criterion;
import jwebform.validation.Validator;

public class TextTypeBuilder extends TypeBuilder {

  private String label = "";
  private String helptext = "";
  private String placeholder = "";
  private Criterion[] criteria = new Criterion[0];

  private Supplier<Element> typeSupplier;


  public TextTypeBuilder withTypeSupplier(Supplier<Element> typeSupplier) {
    this.typeSupplier = typeSupplier;
    return this;
  }

  public TextTypeBuilder withLabel(String label) {
    this.label = label;
    return this;
  }

  public TextTypeBuilder withHelptext(String helptext) {
    this.helptext = helptext;
    return this;
  }

  public TextTypeBuilder withPlaceholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }


  public TextTypeBuilder withCriteria(Criterion... criteria) {
    this.criteria = criteria;
    return this;
  }


  @Override
  public ElementContainer build() {
    return new ElementContainer(typeSupplier.get(), new Validator(criteria),
        new Decoration(label, helptext, placeholder));
  }



}
