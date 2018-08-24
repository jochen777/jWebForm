package jwebform.field.structure;

import jwebform.processor.FieldResults;
import jwebform.validation.ValidationResult;

import javax.annotation.Generated;

// that what is coming out of a "run" method of an element
public final class FieldResult {

  private final ValidationResult validationResult; // not static
  private final String value; // not static
  private final Object valueObject;

  private final StaticFieldInfo staticElementInfo;
  private final FieldResults childs;

  private static final String EMPTY_STRING = "";
  public static final String NO_NAME = "";
  public static final FieldResults NOCHILDS = new FieldResults();

  @Generated("SparkTools")
  private FieldResult(Builder builder) {
    this.validationResult = builder.validationResult;
    this.value = builder.value;
    this.valueObject = builder.valueObject;
    this.staticElementInfo = builder.staticElementInfo;
    this.childs = builder.childs;
  }

  // TODO: Remove validationResult entirely here! This can move to producerInfos



  public FieldResult ofValidationResult(ValidationResult vr) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticElementInfo)
        .withValidationResult(vr).withValue(value).withValueObject(valueObject).build();
  }

  public FieldResult cloneWithChilds(FieldResults childs) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticElementInfo)
        .withValidationResult(validationResult).withValue(value).withValueObject(valueObject)
        .build();
  }


  public FieldResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticElementInfo)
        .withValidationResult(newValidationResult).withValue(value).withValueObject(valueObject)
        .build();
  }

  public ValidationResult getValidationResult() {
    return validationResult;
  }

  public String getValue() {
    return value;
  }

  public FieldResults getChilds() {
    return childs;
  }



  public StaticFieldInfo getStaticElementInfo() {
    return staticElementInfo;
  }

  public Object getValueObject() {
    return valueObject;
  }

  @Override
  public String toString() {
    return String.format("FieldResult: %s", this.value);
  }

  /**
   * Creates builder to build {@link FieldResult}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link FieldResult}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private ValidationResult validationResult = ValidationResult.undefined();
    private String value = EMPTY_STRING;
    private Object valueObject = EMPTY_STRING;
    private StaticFieldInfo staticElementInfo;
    private FieldResults childs = new FieldResults();

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

    public Builder withStaticFieldInfo(StaticFieldInfo staticElementInfo) {
      this.staticElementInfo = staticElementInfo;
      return this;
    }

    public Builder withChilds(FieldResults childs) {
      this.childs = childs;
      return this;
    }

    public FieldResult build() {
      return new FieldResult(this);
    }
  }

}
