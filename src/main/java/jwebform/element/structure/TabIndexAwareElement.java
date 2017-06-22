package jwebform.element.structure;

public interface TabIndexAwareElement extends Element {

  /*
   *  returns the tabIndex Incrementation for this element.
   *  This is normally 1. But if you have a more complex object (that includes 3 text-inputs), you can return more than 1 
   */
  public int getTabIndexIncrement();
}
