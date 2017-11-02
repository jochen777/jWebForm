package jwebform.element.renderer.bootstrap;

import jwebform.element.CheckBoxType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;

public class BootstrapCheckboxRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration = ((CheckBoxType) pi.getElementResult().getSource()).decoration;
    boolean checked = ((CheckBoxType) pi.getElementResult().getSource()).checked;
    System.err.println("Checked: " + checked);

    BootstrapRenderer renderer = new BootstrapRenderer(pi, decoration);

    String errorClass = renderer.calculateErrorClass();

    String errorMessage = renderer.renderErrorMessage();

    String labelStr = renderer.generateLabel();

    String helpHTML = renderer.renderHelpText();

    String aria = renderer.renderAriaDescribedBy();

    String val = renderer.renderValue();
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" name=\""
        + pi.getNameOfInput() + "\" value" + val + (checked ? " checked" : "") + aria + ">";

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();
  }

}
