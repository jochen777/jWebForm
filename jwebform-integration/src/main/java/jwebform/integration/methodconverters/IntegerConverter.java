package jwebform.integration.methodconverters;

import jwebform.field.NumberType;
import jwebform.field.structure.FieldType;

import java.lang.reflect.Field;

import static jwebform.field.builder.BuildInType.number;
import static jwebform.field.builder.BuildInType.text;

public class IntegerConverter implements MethodConverter{

  @Override public FieldType convert(Field m, String parametername, Class clasz, Object root) {
    try {
      Integer i = (Integer) m.get(root);
      if (i==null) {
        return new NumberType(parametername, 0);
      } else {
        return new NumberType(parametername, i);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(Integer.class.getName());
  }
}
