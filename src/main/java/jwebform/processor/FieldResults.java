package jwebform.processor;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.validation.ValidationResult;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// Holds the elementResultMap and the elements itself
public class FieldResults implements Iterable<Map.Entry<Field, FieldResult>> {
  private final Map<Field, FieldResult> elementResultMap;

  public FieldResults(Map<Field, FieldResult> elementResultMap) {
    this.elementResultMap = elementResultMap;
  }

  public FieldResults() {
    this.elementResultMap = new LinkedHashMap<>();
  }

  public void put(Field container, FieldResult result) {
    // RFE: Not really final here!! :( We need something like vavr here to solve this efficiently
    elementResultMap.put(container, result);
  }

  public boolean containsElement(Field container) {
    return elementResultMap.containsKey(container);
  }

  public FieldResult get(Field element) {
    return elementResultMap.get(element);
  }

  @Override
  public Iterator<Entry<Field, FieldResult>> iterator() {
    return elementResultMap.entrySet().iterator();
  }

  public String getElementStringValue(String elementName) {
    for (FieldResult i : elementResultMap.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValue();
      }
    }
    throw new IllegalArgumentException(
        String.format("The element named %s does not exist in form", elementName));
  }

  public final Object getObectValue(String elementName) {
    for (FieldResult i : elementResultMap.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValueObject();
      }
    }
    throw new ElementNotFoundException(
        "This element does not exist in form", elementName);
  }

  public final FieldValdationResults computeSingleElementValidation(String elementName, ValidationResult vr) {
    return FieldValdationResults.of(getElement(elementName), vr);
  }

  public Field getElement(String elementName) {
    for (Map.Entry<Field, FieldResult> entry : elementResultMap.entrySet()) {
      if (elementName.equals(entry.getValue().getStaticElementInfo().getName())) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("This name is not within the form!");
  }

  public Object size() {
    return elementResultMap.size();
  }

  public Set<Field> getContainers() {
    // TODO Auto-generated method stub
    return elementResultMap.keySet();
  }

  public class ElementNotFoundException extends RuntimeException {
    private final String elementName;
    public ElementNotFoundException(String msg, String elementName) {
      super(msg);
      this.elementName = elementName;
    }

    public String getElementName() {
      return elementName;
    }
  }

}
