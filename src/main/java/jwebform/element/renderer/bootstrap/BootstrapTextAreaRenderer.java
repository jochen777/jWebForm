package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;

import jwebform.element.TextAreaType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapTextAreaRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    BootstrapRenderer renderer = new BootstrapRenderer(pi,
        ((TextAreaType) pi.getElementResult().getSource()).oneValueElement.decoration);


    return renderer.renderInputComplex("textarea", Escape.html(pi.getElementResult().getValue()));
  }

}
