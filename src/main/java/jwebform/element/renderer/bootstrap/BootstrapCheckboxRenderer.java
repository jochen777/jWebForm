package jwebform.element.renderer.bootstrap;

import jwebform.element.CheckBoxType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;

public class BootstrapCheckboxRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration = ((CheckBoxType) pi.getElementResult().getSource()).decoration;
    boolean checked = (Boolean) pi.getElementResult().getValueObject();

    BootstrapRenderer renderer = new BootstrapRenderer();

    String aria = renderer.renderAriaDescribedBy(pi, decoration);

    String val = renderer.renderValue(pi);
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" name=\""
        + pi.getNameOfInput() + "\" value" + val + (checked ? " checked" : "") + aria + ">";


    return renderer.renderInputFree(inputHtml, pi, decoration);
  }

}
