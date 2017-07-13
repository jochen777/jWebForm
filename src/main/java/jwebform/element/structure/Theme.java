package jwebform.element.structure;

import java.util.Map;

public interface Theme {
	Map<String, HTMLProducer> getHtmlProducer();

  default HTMLProducer getProducer(Themable themeableElement) {
    HTMLProducer producer = getHtmlProducer().get(themeableElement.getKey());
    if (producer == null) {
      producer = themeableElement.getDefault();
    }
    return producer;
  }
}