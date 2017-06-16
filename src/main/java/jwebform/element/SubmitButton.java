package jwebform.element;

import jwebform.element.structure.TabIndexAwareElement;

public class SubmitButton implements TabIndexAwareElement {

  String label = "Submit";
  int tabIndex = 0;

  public SubmitButton(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @Override
  public String getHtml() {
    return "<input tabindex=\"" + tabIndex + "\" type=\"submit\" value=\"" + label + "\">";
  }

  @Override
  public int feedTabIndex(int currentTabIndex) {
    tabIndex = currentTabIndex;
    return currentTabIndex+1;
  }

  public int getTabIndex() {
    return tabIndex;
  }

}
