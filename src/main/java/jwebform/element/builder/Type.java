package jwebform.element.builder;

import jwebform.element.TextType;
import jwebform.element.structure.ElementContainer;

public class Type {
  public static TextTypeBuilder.Builder text(String name) {
    return TextTypeBuilder.builder().withName(name);
  }
}
