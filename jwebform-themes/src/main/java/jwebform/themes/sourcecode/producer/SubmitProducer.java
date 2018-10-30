package jwebform.themes.sourcecode.producer;

import jwebform.field.SubmitType;
import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.Theme;
import jwebform.model.ProducerInfos;

public class SubmitProducer implements HTMLProducer {

  private final Theme theme;

  public SubmitProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    SubmitType type = (SubmitType) pi.getType();
    return "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"submit\" name=\"" + type.name
        + "\" value=\"" + type.label + "\">\n";
  }

}
