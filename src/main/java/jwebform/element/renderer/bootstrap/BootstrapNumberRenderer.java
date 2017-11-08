package jwebform.element.renderer.bootstrap;

import jwebform.element.NumberType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapNumberRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    ElementRenderer renderer = new BootstrapRenderer();

    return renderer.renderInput("number", pi,
        ((NumberType) pi.getElementResult().getSource()).oneValueElement.decoration);
  }

}
