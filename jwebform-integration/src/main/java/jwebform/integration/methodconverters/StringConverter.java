package jwebform.integration.methodconverters;

import jwebform.field.TextType;
import jwebform.field.structure.FieldType;
import jwebform.integration.annotations.UseFieldType;

import java.lang.reflect.Field;

import static jwebform.field.builder.BuildInType.text;

public class StringConverter implements MethodConverter{

  @Override public FieldType convert(
    Field m, String parametername, Class clasz, Object root) throws IllegalAccessException{
      return new TextType(parametername, (String) m.get(root));
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(String.class.getName());
  }
}
