package jwebform.processors;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.validation.ValidationResult;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// Holds the elementResultMap and the elements itself
public class ElementResults implements Iterable<Map.Entry<ElementContainer, ElementResult>> {
  private final Map<ElementContainer, ElementResult> elementResultMap;

  public ElementResults(Map<ElementContainer, ElementResult> elementResultMap) {
    this.elementResultMap = elementResultMap;
  }

  public ElementResults() {
    this.elementResultMap = new LinkedHashMap<>();
  }

  public void put(ElementContainer container, ElementResult result) {
    // RFE: Not really final here!! :( We need something like vavr here to solve this efficiently
    elementResultMap.put(container, result);
  }

  public boolean containsElement(ElementContainer container) {
    return elementResultMap.containsKey(container);
  }

  public ElementResult get(ElementContainer element) {
    return elementResultMap.get(element);
  }

  @Override
  public Iterator<Entry<ElementContainer, ElementResult>> iterator() {
    return elementResultMap.entrySet().iterator();
  }

  public String getElementStringValue(String elementName) {
    for (ElementResult i : elementResultMap.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValue();
      }
    }
    throw new IllegalArgumentException(
        String.format("The element named %s does not exist in form", elementName));
  }

  public final Object getObectValue(String elementName) {
    for (ElementResult i : elementResultMap.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValueObject();
      }
    }
    throw new ElementNotFoundException(
        "This element does not exist in form", elementName);
  }

  public final ElementValdationResults computeSingleElementValidation(String elementName, ValidationResult vr) {
    return ElementValdationResults.of(getElement(elementName), vr);
  }

  public ElementContainer getElement(String elementName) {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResultMap.entrySet()) {
      if (elementName.equals(entry.getValue().getStaticElementInfo().getName())) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("This name is not within the form!");
  }

  public Object size() {
    return elementResultMap.size();
  }

  public Set<ElementContainer> getContainers() {
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
