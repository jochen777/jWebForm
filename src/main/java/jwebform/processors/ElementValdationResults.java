package jwebform.processors;

import jwebform.element.structure.ElementContainer;
import jwebform.validation.ValidationResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// represents some Element-containers with their ValidationResults. Normally a result of a validator
public class ElementValdationResults {
  private final Map<ElementContainer, ValidationResult> resutls ;

  public static ElementValdationResults of (ElementContainer container, ValidationResult validationResult){
    ElementValdationResults r = new ElementValdationResults();
    r.put(container, validationResult);
    return r;
  }

  public static ElementValdationResults empty() {
    return new ElementValdationResults();
  }

  public ElementValdationResults(Map<ElementContainer, ValidationResult> resutls) {
    this.resutls = resutls;
  }

  public ElementValdationResults() {
    this.resutls = new LinkedHashMap<>();
  }

  public void merge(ElementValdationResults validate) {
    resutls.putAll(validate.getResutls());
  }

  public Map<ElementContainer, ValidationResult> getResutls() {
    return resutls;
  }

  public void put(ElementContainer textInput, ValidationResult not_ok) {
    resutls.put(textInput, not_ok);
  }
}
