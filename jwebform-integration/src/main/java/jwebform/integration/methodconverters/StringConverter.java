package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;
import jwebform.integration.annotations.UseFieldType;

import static jwebform.field.builder.BuildInType.text;

public class StringConverter implements MethodConverter{

  @Override public Field convert(
    java.lang.reflect.Field m, String parametername, Class clasz) {
      return text(parametername, "").label(parametername).build();
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(String.class.getName());
  }
}
