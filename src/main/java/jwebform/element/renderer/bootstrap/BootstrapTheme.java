package jwebform.element.renderer.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.AbstractBehaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Theme;
import jwebform.element.structure.Wrapper;
import jwebform.validation.criteria.Criteria;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  private final BootstrapRenderer renderer = new BootstrapRenderer();
  private final List<AbstractBehaviour> globalBehaviours = new ArrayList<>();

  private final static BootstrapTheme instance = new BootstrapTheme();

  public static BootstrapTheme instance() {
    return instance;
  }

  private BootstrapTheme() {
    htmlProducer = new HashMap<>();
    globalBehaviours.add(new AbstractBehaviour() {
      @Override
      public Wrapper wrapLabel(ProducerInfos pi) {
        // TODO: Check, why pi.getElementContainer might be null! This should not be the case!
        if (pi.getElementContainer() != null && pi.getElementContainer().validator != null
            && pi.getElementContainer().validator.containsCriterion(Criteria.required())) {
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
  public List<AbstractBehaviour> getGlobalBehaviours() {
    return globalBehaviours;
  }


}
