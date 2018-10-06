package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;

import static jwebform.field.builder.BuildInType.number;
import static jwebform.field.builder.BuildInType.text;

public class IntegerConverter implements MethodConverter{

  @Override public Field convert(java.lang.reflect.Field m, String parametername, Class clasz) {
    return number(parametername).label(parametername).build();
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(Integer.class.getName());
  }
}
