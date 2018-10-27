package jwebform.integration.beanvalidation;

import java.util.HashMap;
import java.util.Map;

// describes a validation that comes from extern
// example "maxlen", max=50
public class ExternalValidationDescription {
  public final String name;
  public final Map<String, Object> parameters;

  public ExternalValidationDescription(String name) {
    this(name, new HashMap<>());
  }

  public ExternalValidationDescription(String name, Map<String, Object> parameters) {
    this.name = name;
    this.parameters = parameters;
  }

}
