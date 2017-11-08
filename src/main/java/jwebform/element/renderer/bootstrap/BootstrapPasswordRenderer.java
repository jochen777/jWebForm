package jwebform.element.renderer.bootstrap;

import jwebform.element.PasswordType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapPasswordRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    ElementRenderer renderer = new BootstrapRenderer();

    return renderer.renderInput("password", pi,
        ((PasswordType) pi.getElementResult().getSource()).oneValueElement.decoration);
  }

}
