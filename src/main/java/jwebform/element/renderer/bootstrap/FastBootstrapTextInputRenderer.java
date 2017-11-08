package jwebform.element.renderer.bootstrap;

import jwebform.element.TextType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class FastBootstrapTextInputRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {

    ElementRenderer renderer = new BootstrapRenderer();

    return renderer.renderInput("text", pi,
        ((TextType) pi.getElementResult().getSource()).oneValueElement.decoration);
  }

}
