package jwebform.element.renderer.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.element.structure.Behaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Theme;
import jwebform.element.structure.Wrapper;
import jwebform.validation.criteria.Criteria;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  private final BootstrapRenderer renderer = new BootstrapRenderer();
  private final List<Behaviour> globalBehaviours = new ArrayList<>();

  private final static BootstrapTheme instance = new BootstrapTheme();
  
  public static BootstrapTheme instance() {
    return instance;
  }
  
  private BootstrapTheme() {
    htmlProducer = new HashMap<>();
    globalBehaviours.add(new Behaviour() {
      @Override
      public Wrapper wrapLabel(ProducerInfos pi) {
        if (pi.getElementContainer().validator.containsCriterion(Criteria.required())) {
          return Wrapper.of("", " *");
        } else {
          return Wrapper.empty();
        }
      }
    });
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
