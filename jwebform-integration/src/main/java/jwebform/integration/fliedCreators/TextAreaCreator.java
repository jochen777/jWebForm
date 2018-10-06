package jwebform.integration.fliedCreators;

import jwebform.field.TextAreaType;
import jwebform.field.structure.Field;

public class TextAreaCreator implements FieldCreator {
  @Override public Field createField(
    Class c, String name, Object... constructor) {
    return new Field(new TextAreaType(name, ""));
  }

  @Override public Class supportsFieldType() {
    return TextAreaType.class;
  }
}
