package jwebform.processor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.validation.ValidationResult;

/**
 * Holds the field results of the form.
  */
public class FieldResults implements Iterable<Map.Entry<Field, FieldResult>> {
  private final Map<Field, FieldResult> fieldResultMap;

  public FieldResults(Map<Field, FieldResult> fieldResultMap) {
    this.fieldResultMap = fieldResultMap;
  }

  public FieldResults() {
    this.fieldResultMap = new LinkedHashMap<>();
  }

  public void put(Field container, FieldResult result) {
    // RFE: Not really final here!! :( We need something like vavr here to solve this efficiently
    fieldResultMap.put(container, result);
  }

  public boolean containsField(Field container) {
    return fieldResultMap.containsKey(container);
  }

  public FieldResult get(Field field) {
    return fieldResultMap.get(field);
  }

  @Override
  public Iterator<Entry<Field, FieldResult>> iterator() {
    return fieldResultMap.entrySet().iterator();
  }

  public String getFieldStringValue(String fieldName) {
    for (FieldResult i : fieldResultMap.values()) {
      if (fieldName.equals(i.getStaticFieldInfo().getName())) {
        return i.getValue();
      }
    }
    throw new IllegalArgumentException(
        String.format("The field named '%s' does not exist in form", fieldName));
  }

  public final Object getObectValue(String fieldName) {
    for (FieldResult i : fieldResultMap.values()) {
      if (fieldName.equals(i.getStaticFieldInfo().getName())) {
        return i.getValueObject();
      }
    }
    throw new FieldNotFoundException("This fieldType does not exist in form", fieldName);
  }

  public final FieldValdationResults computeSingleFieldValidation(String fieldName,
      ValidationResult vr) {
    return FieldValdationResults.of(getField(fieldName), vr);
  }

  public Field getField(String fieldName) {
    for (Map.Entry<Field, FieldResult> entry : fieldResultMap.entrySet()) {
      if (fieldName.equals(entry.getValue().getStaticFieldInfo().getName())) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("This name is not within the form!");
  }

  public Object size() {
    return fieldResultMap.size();
  }

  public Set<Field> getContainers() {
    return fieldResultMap.keySet();
  }

  public class FieldNotFoundException extends RuntimeException {
    private final String fieldName;

    public FieldNotFoundException(String msg, String fieldName) {
      super(msg);
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }
  }

}
