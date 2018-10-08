package jwebform.integration.methodconverters;

import jwebform.field.TextDateType;
import jwebform.field.structure.FieldType;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static jwebform.field.builder.BuildInType.text;
import static jwebform.field.builder.BuildInType.textDate;

public class LocalDateConverter implements MethodConverter{

  @Override public FieldType convert(
    Field m, String parametername, Class clasz, Object root) throws IllegalAccessException {
      return new TextDateType(parametername, (LocalDate)m.get(root));
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(LocalDate.class.getName());
  }
}
