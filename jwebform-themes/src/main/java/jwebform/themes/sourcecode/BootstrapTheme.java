package jwebform.themes.sourcecode;

import java.util.HashMap;
import java.util.Map;
import jwebform.FormResult;
import jwebform.field.structure.HTMLProducer;
import jwebform.integration.MessageSource;
import jwebform.themes.common.StartEndRenderer;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;
  protected final ElementRenderer renderer;
  protected final RadioRenderer radioRenderer;


  private static BootstrapTheme instance;

  // RFE: This is ugly, if you have more than one BootstrapTheme instance in your app. Depreate
  // this!
  @Deprecated
  public static BootstrapTheme instance(MessageSource messageSource) {
    if (instance == null) {
      instance = new BootstrapTheme(messageSource);
    }
    return instance;
  }

  @Deprecated
  public static BootstrapTheme instance() {
    if (instance == null) {
      instance = new BootstrapTheme(k -> k);
    }
    return instance;
  }

  public BootstrapTheme(ElementRenderer renderer, RadioRenderer radioRenderer) {
    this.renderer = renderer;
    this.radioRenderer = radioRenderer;
    htmlProducer = new HashMap<>();
  }

  public BootstrapTheme(MessageSource messageSource) {
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
