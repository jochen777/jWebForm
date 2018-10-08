package jwebform.integration.methodconverters;

import jwebform.field.CheckBoxType;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;

import static jwebform.field.builder.BuildInType.checkbox;
import static jwebform.field.builder.BuildInType.text;

public class BooleanConverter implements MethodConverter{

  @Override public FieldType convert(
    java.lang.reflect.Field m, String parametername, Class clasz, Object root) {

    try {
      return new CheckBoxType(parametername, (boolean) m.get(root));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(Boolean.class.getName()) || clasz.getName().equals("boolean");
  }
}
