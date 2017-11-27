package jwebform.element.renderer.bootstrap;

import jwebform.view.MessageSource;

public class TwoColumnBootstrapTheme extends BootstrapTheme {

  private static TwoColumnBootstrapTheme instance;


  private TwoColumnBootstrapTheme(MessageSource messageSource) {
    super(new TwoColumnBootstrapRenderer(messageSource), new BootstrapRadioRenderer());
  }


  public static TwoColumnBootstrapTheme instance() {
    if (instance == null) {
      instance = new TwoColumnBootstrapTheme(k -> k);
    }
    return instance;
  }
}
