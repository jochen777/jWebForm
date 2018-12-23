package jwebform.themes.sourcecode.producer;

import jwebform.field.SubmitType;
import jwebform.field.structure.HTMLProducer;
import jwebform.model.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class SubmitProducer implements HTMLProducer {

  private final Theme theme;

  public SubmitProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    SubmitType type = (SubmitType) pi.getType();
    return "<button class=\"btn btn-primary\" tabindex=\"" + pi.getTabIndex()
        + "\" type=\"submit\" name=\"" + type.name + "\">" + pi.getDecoration().getLabel()
        + "</button>\n";
  }

}
