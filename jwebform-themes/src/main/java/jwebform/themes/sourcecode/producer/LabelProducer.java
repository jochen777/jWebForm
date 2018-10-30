package jwebform.themes.sourcecode.producer;

import jwebform.field.LabelType;
import jwebform.field.structure.HTMLProducer;
import jwebform.model.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class LabelProducer implements HTMLProducer {

  private final Theme theme;
  
  public LabelProducer(Theme theme) {
    this.theme = theme;
  }
  
  @Override
  public String getHTML(ProducerInfos pi) {
    LabelType type = (LabelType) pi.getType();
      return "<label>" + type.label + "</label>";
  }

}
