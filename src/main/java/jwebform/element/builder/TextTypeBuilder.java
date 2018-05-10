package jwebform.element.builder;

import javax.annotation.Generated;

import jwebform.element.TextType;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ElementContainer;
import jwebform.validation.Criterion;
import jwebform.validation.Validator;

public class TextTypeBuilder {
  String name;
  String initialValue;
  Criterion[] criteria;
  Decoration decoration;

  @Generated("SparkTools")
  private TextTypeBuilder(Builder builder) {
    this.name = builder.name;
    this.initialValue = builder.initialValue;
    this.criteria = builder.criteria;
    this.decoration = builder.decoration;
  }

  /**
   * Creates builder to build {@link TextTypeBuilder}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link TextTypeBuilder}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private String name;
    private String initialValue;
    private Criterion[] criteria;
    private Decoration decoration;

    private Builder() {}

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withInitialValue(String initialValue) {
      this.initialValue = initialValue;
      return this;
    }

    public Builder withCriteria(Criterion... criterias) {
      this.criteria = criterias;
      return this;
    }

    public Builder withDecoration(Decoration decoration) {
      this.decoration = decoration;
      return this;
    }

    public ElementContainer build() {
      return new ElementContainer(new TextType(name, initialValue), new Validator(criteria),
          decoration);
    }
  }

}
