package jwebform.element.renderer.bootstrap;

import jwebform.element.NumberType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapNumberRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    BootstrapRenderer renderer = new BootstrapRenderer(pi,
        ((NumberType) pi.getElementResult().getSource()).oneValueElement.decoration);

    return renderer.renderInput("number");
  }

}
