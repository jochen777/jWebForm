package jwebform.element;

public interface TabIndexAwareElement extends Element {

  /*
   *  tells an elment about the current tab-index.
   *  This element returns the next TabIndex. 
   *  (Normally +1, but more complex elements can return more than +1) 
   */
  public int feedTabIndex(int currentTabIndex);
}
