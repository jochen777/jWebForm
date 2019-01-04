package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.validation.ValidationResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * represents some fields with their ValidationResults. Normally a result of a validator
 */
public class FieldValdationResults {
  private final Map<Field, ValidationResult> resutls;

  public FieldValdationResults(Map<Field, ValidationResult> resutls) {
    this.resutls = resutls;
  }

  public static FieldValdationResults of(Field container, ValidationResult validationResult) {
    FieldValdationResults r = new FieldValdationResults();
    r.put(container, validationResult);
    return r;
  }

  public static FieldValdationResults empty() {
    return new FieldValdationResults();
  }


  public FieldValdationResults() {
    this.resutls = new LinkedHashMap<>();
  }

  public void merge(FieldValdationResults validate) {
    resutls.putAll(validate.getResutls());
  }

  public Map<Field, ValidationResult> getResutls() {
    return resutls;
  }

  public void put(Field textInput, ValidationResult notOk) {
    resutls.put(textInput, notOk);
  }
}
