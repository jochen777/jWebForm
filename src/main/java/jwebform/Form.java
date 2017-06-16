package jwebform;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.TabIndexAwareElement;

// Represents a form
public class Form {

  List<Element> elements = new ArrayList<>();
  String id = "id";


  public FormResult run(Env env) {
    // sort tab-indexes
    sortTabIndexes();
    
    // check first run. (?)
    
    // validate elements
    
    // validate form
    return new FormResult(this);
  }

  private void sortTabIndexes() {
    int tabIndex = 0;
    for (Element element : elements) {
      if (element instanceof TabIndexAwareElement) {
        tabIndex = ((TabIndexAwareElement) element).feedTabIndex(tabIndex);
      }
    }
  }

  public void addElement(Element element) {
    elements.add(element);
  }

  List<Element> getElements() {
    return elements;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMethod() {
    return "POST"; // TODO: Make this configurable
  }

  public boolean isHtml5Validate() {
    return true; // TODO: Make this configurable
  }

}
