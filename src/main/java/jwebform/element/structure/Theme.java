package jwebform.element.structure;

import java.util.Map;

public interface Theme {
	Map<String, HTMLProducer> getHtmlProducer();

	default HTMLProducer getProducer(Themable themeableElement) {
		return getHtmlProducer().getOrDefault(themeableElement.getKey(), themeableElement.getDefault());
	}
}