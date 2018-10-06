package jwebform.integration.fliedCreators;

import jwebform.field.structure.Field;

public interface FieldCreator {

  public Field createField(Class c, String name, Object... constructor);

  public Class supportsFieldType();
}
