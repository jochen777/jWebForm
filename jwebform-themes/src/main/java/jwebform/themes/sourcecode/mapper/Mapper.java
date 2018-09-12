package jwebform.themes.sourcecode.mapper;

import jwebform.field.structure.FieldType;
import jwebform.field.structure.HTMLProducer;

import java.util.Optional;

// resolves from every Element the HTML Producer
public interface Mapper {
  public Optional<HTMLProducer> fromElement(FieldType element);
}
