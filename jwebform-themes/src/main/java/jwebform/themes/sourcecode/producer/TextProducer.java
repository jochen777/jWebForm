package jwebform.themes.sourcecode.producer;

import jwebform.field.structure.HTMLProducer;
import jwebform.model.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class TextProducer implements HTMLProducer {

  private final Theme theme;
  
  public TextProducer(Theme theme) {
    this.theme = theme;
  }
  
  @Override
  public String getHTML(ProducerInfos pi) {
    return theme.getRenderer().renderInput("text", pi, pi.getDecoration(), "");
  }

}
