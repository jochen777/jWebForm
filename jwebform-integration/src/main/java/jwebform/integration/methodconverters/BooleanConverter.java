package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;

import static jwebform.field.builder.BuildInType.checkbox;
import static jwebform.field.builder.BuildInType.text;

public class BooleanConverter implements MethodConverter{

  @Override public Field convert(
    java.lang.reflect.Field m, String parametername, Class clasz) {
      return checkbox(parametername).label(parametername).build();
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(Boolean.class.getName());
  }
}
