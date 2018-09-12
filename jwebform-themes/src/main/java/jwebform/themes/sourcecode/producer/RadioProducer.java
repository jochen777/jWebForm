package jwebform.themes.sourcecode.producer;

import jwebform.field.RadioType;
import jwebform.field.structure.HTMLProducer;
import jwebform.view.ProducerInfos;
import jwebform.themes.sourcecode.ElementRenderer;
import jwebform.themes.sourcecode.Theme;

public class RadioProducer implements HTMLProducer {

  private final Theme theme;
  
  public RadioProducer(Theme theme) {
    this.theme = theme;
  }
  
  @Override
  public String getHTML(ProducerInfos pi) {
    RadioType type = (RadioType) pi.getType();
    
    return theme.getRenderer().renderInputFree(
        theme.getRadioRenderer(theme).renderInputs(pi, type.entries, theme), pi,
        pi.getDecoration(), ElementRenderer.InputVariant.radio);
  }

}
