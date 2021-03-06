package jwebform.themes.sourcecode.producer;

import com.coverity.security.Escape;
import jwebform.field.structure.HTMLProducer;
import jwebform.model.ProducerInfos;
import jwebform.themes.sourcecode.Theme;

public class TextAreaProducer implements HTMLProducer {

  private final Theme theme;

  public TextAreaProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    return theme.getRenderer().renderInputComplex("textarea", Escape.html(pi.getValue()), pi,
        pi.getDecoration());
  }

}
