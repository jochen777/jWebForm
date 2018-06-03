package jwebform.processors;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

// Holds the elementResults and the elements itself
public class ElementResults implements Iterable<Map.Entry<ElementContainer, ElementResult>> {
  private final Map<ElementContainer, ElementResult> elementResults;

  public ElementResults(Map<ElementContainer, ElementResult> elementResults) {
    this.elementResults = elementResults;
  }

  public ElementResults() {
    this.elementResults = new LinkedHashMap<>();
  }

  public void put(ElementContainer container, ElementResult result) {
    // RFE: Not really final here!! :( We need something like vavr here to solve this efficiently
    elementResults.put(container, result);
  }

  public boolean containsElement(ElementContainer container) {
    return elementResults.containsKey(container);
  }

  public ElementResult get(ElementContainer element) {
    return elementResults.get(element);
  }

  @Override
  public Iterator<Entry<ElementContainer, ElementResult>> iterator() {
    return elementResults.entrySet().iterator();
  }

  public String getElementStringValue(String elementName) {
    for (ElementResult i : elementResults.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValue();
      }
    }
    throw new IllegalArgumentException(
        String.format("The element named %s does not exist in form", elementName));
  }

  public final Object getObectValue(String elementName) {
    for (ElementResult i : elementResults.values()) {
      if (elementName.equals(i.getStaticElementInfo().getName())) {
        return i.getValueObject();
      }
    }
    throw new RuntimeException(
        String.format("The element named %s does not exist in form", elementName));
  }


  public ElementContainer getElement(String elementName) {
    for (Map.Entry<ElementContainer, ElementResult> entry : elementResults.entrySet()) {
      if (elementName.equals(entry.getValue().getStaticElementInfo().getName())) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("This name is not within the form!");
  }

  public Object size() {
    return elementResults.size();
  }

  public Set<ElementContainer> getContainers() {
    // TODO Auto-generated method stub
    return elementResults.keySet();
  }


}
