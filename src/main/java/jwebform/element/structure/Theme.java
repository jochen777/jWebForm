package jwebform.element.structure;

import java.util.Map;

import jwebform.element.renderer.bootstrap.ElementRenderer;

public interface Theme {
  Map<String, HTMLProducer> getHtmlProducer();

  ElementRenderer getRenderer();
}
