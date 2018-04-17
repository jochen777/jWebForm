package jwebform.element.structure;

@FunctionalInterface
public interface HTMLProducer {
  String getHTML(ProducerInfos producerInfos);

  static HTMLProducer emptyHtmlProducer() {
    return t -> "<!-- empty -->";
  }
}
