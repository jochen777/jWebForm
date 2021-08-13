package jwebform.field.structure;

import jwebform.processor.FieldResults;
import jwebform.validation.ValidationResult;


/**
 * that what is coming out of a "run" method of an fieldType
 *
 */
public final class FieldResult {

  private final ValidationResult validationResult;
  private final String value;
  private final Object valueObject;

  private final StaticFieldInfo staticFieldInfo;
  private final FieldResults childs;

  // some "constants"
  private static final String EMPTY_STRING = "";
  public static final String NO_NAME = "";
  public static final FieldResults NOCHILDS = new FieldResults();

  private FieldResult(Builder builder) {
    this.validationResult = builder.validationResult;
    this.value = builder.value;
    this.valueObject = builder.valueObject;
    this.staticFieldInfo = builder.internalStaticFieldInfo;
    this.childs = builder.childs;
  }


  public FieldResult ofValidationResult(ValidationResult vr) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticFieldInfo)
        .withValidationResult(vr).withValue(value).withValueObject(valueObject).build();
  }

  public FieldResult cloneWithChilds(FieldResults childs) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticFieldInfo)
        .withValidationResult(validationResult).withValue(value).withValueObject(valueObject)
        .build();
  }


  public FieldResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
    return FieldResult.builder().withChilds(childs).withStaticFieldInfo(staticFieldInfo)
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



  public StaticFieldInfo getStaticFieldInfo() {
    return staticFieldInfo;
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
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link FieldResult}.
   */
  public static final class Builder {
    private ValidationResult validationResult = ValidationResult.undefined();
    private String value = EMPTY_STRING;
    private Object valueObject = EMPTY_STRING;
    private StaticFieldInfo internalStaticFieldInfo;
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

    public Builder withStaticFieldInfo(StaticFieldInfo staticFieldInfo) {
      this.internalStaticFieldInfo = staticFieldInfo;
      return this;
    }

    public Builder withChilds(FieldResults childs) {
      this.childs = childs;
      return this;
    }

    public FieldResult build() {
      // if someone did only call withValue and did not call withValueObject we set the valueObject automatically
      if (valueObject == EMPTY_STRING && value != EMPTY_STRING) {
        valueObject = value;
      }

      return new FieldResult(this);
    }
  }

}
