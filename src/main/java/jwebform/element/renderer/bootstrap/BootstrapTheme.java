package jwebform.element.renderer.bootstrap;

import java.util.HashMap;
import java.util.Map;

import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  private final BootstrapRenderer renderer = new BootstrapRenderer();

  public BootstrapTheme() {
    htmlProducer = new HashMap<>();
  }

  public Map<String, HTMLProducer> getHtmlProducer() {
    return htmlProducer;
  }

  @Override
  public ElementRenderer getRenderer() {
    return renderer;
  }
}
