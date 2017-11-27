package jwebform.element.renderer.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jwebform.element.structure.Behaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RadioRenderer;
import jwebform.view.MessageSource;
import jwebform.view.StartEndRenderer;
import jwebform.view.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  private final BootstrapRenderer renderer;
  private final BootstrapRadioRenderer radioRenderer;
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
      instance = new BootstrapTheme(k -> k);
    }
    return instance;
  }


  private BootstrapTheme(MessageSource messageSource) {
    this.renderer = new BootstrapRenderer(messageSource);
    this.radioRenderer = new BootstrapRadioRenderer();
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

  @Override
  public String getStart(
      String formId,
      String method,
      boolean html5Validation,
      boolean uplaodform) {
    StartEndRenderer start = new StartEndRenderer(formId, method, html5Validation, uplaodform);
    return start.getStart() + "<fieldset>";
  }

  @Override
  public String getEnd() {
    return "</fieldset></form>";
  }

  @Override
  public RadioRenderer getRadioRenderer() {
    return radioRenderer;
  }



}
