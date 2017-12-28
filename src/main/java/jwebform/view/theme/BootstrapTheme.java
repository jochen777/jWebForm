package jwebform.view.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.behaviour.Behaviour;
import jwebform.view.ElementRenderer;
import jwebform.view.MessageSource;
import jwebform.view.RadioRenderer;
import jwebform.view.StartEndRenderer;
import jwebform.view.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  protected final ElementRenderer renderer;
  protected final RadioRenderer radioRenderer;
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

  protected BootstrapTheme(ElementRenderer renderer, RadioRenderer radioRenderer) {
    this.renderer = renderer;
    this.radioRenderer = radioRenderer;
    htmlProducer = new HashMap<>();
  }

  protected BootstrapTheme(MessageSource messageSource) {
    this(new BootstrapRenderer(messageSource), new BootstrapRadioRenderer());
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
