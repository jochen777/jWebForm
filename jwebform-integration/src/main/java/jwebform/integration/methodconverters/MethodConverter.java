package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldType;

import java.lang.reflect.Method;
import java.util.Optional;

public interface MethodConverter {
  FieldType convert(java.lang.reflect.Field m, String parametername, Class clasz, Object root) throws IllegalAccessException;
  boolean supportsType(Class clasz);
}
