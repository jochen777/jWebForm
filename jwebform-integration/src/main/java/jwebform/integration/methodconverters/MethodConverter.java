package jwebform.integration.methodconverters;

import jwebform.field.structure.Field;

import java.lang.reflect.Method;
import java.util.Optional;

public interface MethodConverter {
  Field convert(java.lang.reflect.Field m, String parametername, Class clasz);
  boolean supportsType(Class clasz);
}
