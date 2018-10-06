package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;

import java.time.LocalDate;

import static jwebform.field.builder.BuildInType.text;
import static jwebform.field.builder.BuildInType.textDate;

public class LocalDateConverter implements MethodConverter{

  @Override public Field convert(
    java.lang.reflect.Field m, String parametername, Class clasz) {
      return textDate(parametername, LocalDate.now()).label(parametername).build();
  }

  @Override public boolean supportsType(Class clasz) {
    return clasz.getName().equals(LocalDate.class.getName());
  }
}
