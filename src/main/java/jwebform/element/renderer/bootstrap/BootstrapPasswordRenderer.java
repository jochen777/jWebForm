package jwebform.element.renderer.bootstrap;

import jwebform.element.PasswordType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapPasswordRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    BootstrapRenderer renderer =
        new BootstrapRenderer(pi, ((PasswordType) pi.getElementResult().getSource()).decoration);

    String errorClass = renderer.calculateErrorClass();

    String errorMessage = renderer.renderErrorMessage();

    String labelStr = renderer.generateLabel();

    String placeholder = renderer.generatePlaceholder();

    String helpHTML = renderer.renderHelpText();

    String aria = renderer.renderAriaDescribedBy();


    String val = renderer.renderValue();
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"password\" name=\""
        + pi.getNameOfInput() + "\" value" + val + placeholder + aria + ">";

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();
  }

}
