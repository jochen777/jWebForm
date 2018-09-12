package jwebform.themes.sourcecode.producer;

import jwebform.field.structure.HTMLProducer;
import jwebform.view.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class UploadProducer implements HTMLProducer {

  private final Theme theme;
  
  public UploadProducer(Theme theme) {
    this.theme = theme;
  }
  
  @Override
  public String getHTML(ProducerInfos pi) {
    return theme.getRenderer().renderInput("file", pi, pi.getDecoration(),
        "");
  }

}
