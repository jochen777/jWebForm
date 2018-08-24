package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.validation.ValidationResult;

import java.util.LinkedHashMap;
import java.util.Map;

// represents some Element-containers with their ValidationResults. Normally a result of a validator
public class ElementValdationResults {
  private final Map<Field, ValidationResult> resutls ;

  public static ElementValdationResults of (Field container, ValidationResult validationResult){
    ElementValdationResults r = new ElementValdationResults();
    r.put(container, validationResult);
    return r;
  }

  public static ElementValdationResults empty() {
    return new ElementValdationResults();
  }

  public ElementValdationResults(Map<Field, ValidationResult> resutls) {
    this.resutls = resutls;
  }

  public ElementValdationResults() {
    this.resutls = new LinkedHashMap<>();
  }

  public void merge(ElementValdationResults validate) {
    resutls.putAll(validate.getResutls());
  }

  public Map<Field, ValidationResult> getResutls() {
    return resutls;
  }

  public void put(Field textInput, ValidationResult not_ok) {
    resutls.put(textInput, not_ok);
  }
}
