package jwebform.field.structure;

import jwebform.view.ProducerInfos;

@FunctionalInterface
public interface HTMLProducer {
  String getHTML(ProducerInfos producerInfos);

  static HTMLProducer emptyHtmlProducer() {
    return t -> "<!-- empty -->";
  }
}
