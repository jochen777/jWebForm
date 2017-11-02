package jwebform.element.renderer.bootstrap;

import jwebform.element.LabelType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapLabelRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos producerInfos) {
    LabelType type = (LabelType) producerInfos.getElementResult().getSource();
    return "<label>" + type.label + "</label>";
  }

}
