package jwebform.field.structure;

import jwebform.model.ProducerInfos;

/**
 * Every Type can produce HTML via this function.
 * It is part of the FieldResult.StaticFieldInfo
 */
@FunctionalInterface
public interface HTMLProducer {
  String getHTML(ProducerInfos producerInfos);

  static HTMLProducer emptyHtmlProducer() {
    return t -> "<!-- empty -->";
  }
}
