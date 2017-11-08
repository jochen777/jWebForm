package jwebform.element.renderer.bootstrap;

import jwebform.element.UploadType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapUploadRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    return pi.getTheme().getRenderer().renderInput("file", pi,
        ((UploadType) pi.getElementResult().getSource()).oneValueElement.decoration);

  }

}
