package jwebform.element.renderer.bootstrap;

import jwebform.element.PasswordType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapPasswordRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    BootstrapRenderer renderer = new BootstrapRenderer(pi,
        ((PasswordType) pi.getElementResult().getSource()).oneValueElement.decoration);

    return renderer.renderInput("password");
  }

}
