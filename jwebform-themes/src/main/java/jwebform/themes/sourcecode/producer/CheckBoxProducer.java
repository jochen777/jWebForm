package jwebform.themes.sourcecode.producer;

import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.ElementRenderer;
import jwebform.themes.sourcecode.Theme;
import jwebform.model.ProducerInfos;

public class CheckBoxProducer implements HTMLProducer {

  private final Theme theme;

  public CheckBoxProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    ElementRenderer renderer = this.theme.getRenderer();
    String aria = renderer.renderAriaDescribedBy(pi, pi.getDecoration());
    String val = pi.getName();
    boolean checked = (boolean) pi.getValueObject();
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" id=\""+pi.getFormId()+"-"+pi.getName()+"\" name=\""
        + pi.getName() + "\" value=\"" + val + "\""+ (checked ? " checked" : "") + aria + " class=\"form-check-input "+
      theme.getRenderer().calculateErrorClass(pi)+ "\" >";
    return renderer.renderInputFree(inputHtml, pi, pi.getDecoration(),
        ElementRenderer.InputVariant.checkbox);
  }

}
