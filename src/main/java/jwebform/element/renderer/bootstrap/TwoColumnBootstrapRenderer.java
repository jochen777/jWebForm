package jwebform.element.renderer.bootstrap;

import jwebform.view.MessageSource;

public class TwoColumnBootstrapRenderer extends BootstrapRenderer {

  public TwoColumnBootstrapRenderer(MessageSource messageSource) {
    super(messageSource);
  }

  protected String getGroupClass() {
    return "col-sm-10";
  }
}
