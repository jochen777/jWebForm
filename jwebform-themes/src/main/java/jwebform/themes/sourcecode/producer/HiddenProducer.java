package jwebform.themes.sourcecode.producer;

import com.coverity.security.Escape;
import jwebform.field.structure.HTMLProducer;
import jwebform.model.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class HiddenProducer implements HTMLProducer {

  private final Theme theme;

  public HiddenProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    return "<input type=\"hidden\" name=\"" + pi.getName() + "\" value=\""
        + Escape.html(pi.getValue()) + "\">\n";
  }

}
