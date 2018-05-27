package jwebform.element.builder;

import jwebform.element.structure.ElementContainer;

public abstract class TypeBuilder {
  public String label;
  public String helptext;
  public String placeholder;

  public abstract ElementContainer build();

}
