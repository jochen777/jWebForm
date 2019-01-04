package jwebform.themes.sourcecode;

import jwebform.FormResult;
import jwebform.field.structure.HTMLProducer;
import jwebform.integration.MessageSource;
import jwebform.themes.common.StartEndRenderer;

import java.util.HashMap;
import java.util.Map;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  protected final ElementRenderer renderer;
  protected final RadioRenderer radioRenderer;


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
  public String getStart(FormResult result, String method, boolean html5Validation) {
    StartEndRenderer start = new StartEndRenderer(result, method, html5Validation);
    return start.getStart() + "<fieldset>\n";
  }

  @Override
  public String getEnd() {
    return "</fieldset></form>";
  }

  @Override
  public RadioRenderer getRadioRenderer(Theme theme) {
    return radioRenderer;
  }



}
