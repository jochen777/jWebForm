package jwebform.themes.sourcecode.producer;

import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.ElementRenderer;
import jwebform.themes.sourcecode.Theme;
import jwebform.view.ProducerInfos;

public class CheckBoxProducer implements HTMLProducer {

  private final Theme theme;

  public CheckBoxProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    ElementRenderer renderer = this.theme.getRenderer();
    String aria = renderer.renderAriaDescribedBy(pi, pi.getDecoration());
    String val = renderer.renderValue(pi.getValue());
    boolean checked = (boolean) pi.getValueObject();
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" name=\""
        + pi.getName() + "\" value" + val + (checked ? " checked" : "") + aria + ">";
    return renderer.renderInputFree(inputHtml, pi, pi.getDecoration(),
        ElementRenderer.InputVariant.checkbox);
  }

}
