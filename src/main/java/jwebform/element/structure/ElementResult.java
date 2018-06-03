package jwebform.element.structure;

import javax.annotation.Generated;
import jwebform.processors.ElementResults;
import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public final class ElementResult {

  private final ValidationResult validationResult; // not static
  private final String value; // not static
  private final Object valueObject;

  private final StaticElementInfo staticElementInfo;
  private final ElementResults childs;

  public static final String EMPTY_STRING = "";
  public static final String NO_NAME = "";
  public static final ElementResults NOCHILDS = new ElementResults();

  @Generated("SparkTools")
  private ElementResult(Builder builder) {
    this.validationResult = builder.validationResult;
    this.value = builder.value;
    this.valueObject = builder.valueObject;
    this.staticElementInfo = builder.staticElementInfo;
    this.childs = builder.childs;
  }

  // TODO: Remove validationResult entirely here! This can move to producerInfos



  public ElementResult ofValidationResult(ValidationResult vr) {
    return ElementResult.builder().withChilds(childs).withStaticElementInfo(staticElementInfo)
        .withValidationResult(vr).withValue(value).withValueObject(valueObject).build();
  }

  public ElementResult cloneWithChilds(ElementResults childs) {
    return ElementResult.builder().withChilds(childs).withStaticElementInfo(staticElementInfo)
        .withValidationResult(validationResult).withValue(value).withValueObject(valueObject)
        .build();
  }


  public ElementResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
    return ElementResult.builder().withChilds(childs).withStaticElementInfo(staticElementInfo)
        .withValidationResult(newValidationResult).withValue(value).withValueObject(valueObject)
        .build();
  }

  public ValidationResult getValidationResult() {
    return validationResult;
  }

  public String getValue() {
    return value;
  }

  public ElementResults getChilds() {
    return childs;
  }



  public StaticElementInfo getStaticElementInfo() {
    return staticElementInfo;
  }

  public Object getValueObject() {
    return valueObject;
  }

  @Override
  public String toString() {
    return String.format("ElementResult: %s", this.value);
  }

  /**
   * Creates builder to build {@link ElementResult}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link ElementResult}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private ValidationResult validationResult = ValidationResult.undefined();
    private String value = EMPTY_STRING;
    private Object valueObject = EMPTY_STRING;
    private StaticElementInfo staticElementInfo;
    private ElementResults childs = new ElementResults();

    private Builder() {}

    public Builder withValidationResult(ValidationResult validationResult) {
      this.validationResult = validationResult;
      return this;
    }

    public Builder withValue(String value) {
      this.value = value;
      return this;
    }

    public Builder withValueObject(Object valueObject) {
      this.valueObject = valueObject;
      return this;
    }

    public Builder withStaticElementInfo(StaticElementInfo staticElementInfo) {
      this.staticElementInfo = staticElementInfo;
      return this;
    }

    public Builder withChilds(ElementResults childs) {
      this.childs = childs;
      return this;
    }

    public ElementResult build() {
      return new ElementResult(this);
    }
  }

}
