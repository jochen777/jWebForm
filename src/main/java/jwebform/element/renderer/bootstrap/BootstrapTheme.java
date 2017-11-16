package jwebform.element.renderer.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Behaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.view.MessageSource;
import jwebform.view.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  private final BootstrapRenderer renderer;
  private final List<Behaviour> globalBehaviours = new ArrayList<>(); // mutual :/

  private static BootstrapTheme instance;

  public static BootstrapTheme instance(MessageSource messageSource) {
    if (instance == null) {
      instance = new BootstrapTheme(messageSource);
    }
    return instance;
  }

  public static BootstrapTheme instance() {
    if (instance == null) {
      instance = new BootstrapTheme(k->k);
    }
    return instance;
  }

  
  private BootstrapTheme(MessageSource messageSource) {
    this.renderer = new BootstrapRenderer(messageSource);
    htmlProducer = new HashMap<>();
  }

  public Map<String, HTMLProducer> getHtmlProducer() {
    return htmlProducer;
  }

  @Override
  public ElementRenderer getRenderer() {
    return renderer;
  }

  @Override
  public List<Behaviour> getGlobalBehaviours() {
    return globalBehaviours;
  }


}
