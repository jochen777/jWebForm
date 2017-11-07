package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;

import jwebform.element.TextAreaType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;

public class BootstrapTextAreaRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    BootstrapRenderer renderer = new BootstrapRenderer(pi,
        ((TextAreaType) pi.getElementResult().getSource()).oneValueElement.decoration);

    String errorClass = renderer.calculateErrorClass();

    String errorMessage = renderer.renderErrorMessage();

    String labelStr = renderer.generateLabel();

    String placeholder = renderer.generatePlaceholder();

    String helpHTML = renderer.renderHelpText();

    String aria = renderer.renderAriaDescribedBy();


    String inputHtml = "<textarea tabindex=\"" + pi.getTabIndex() + "\" type=\"text\" name=\""
        + pi.getNameOfInput() + "\"" + placeholder + aria + ">"
        + Escape.html(pi.getElementResult().getValue()) + "</textarea>";

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();
  }

}
