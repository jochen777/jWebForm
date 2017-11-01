package jwebform.element.renderer.bootstrap;

import java.util.List;

import jwebform.element.SelectType;
import jwebform.element.SelectType.SelectInputEntry;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;

public class BootstrapSelectRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration = ((SelectType) pi.getElementResult().getSource()).decoration;
    List<SelectInputEntry> entries = ((SelectType) pi.getElementResult().getSource()).entries;

    BootstrapRenderer renderer = new BootstrapRenderer(pi, decoration);

    String errorClass = renderer.calculateErrorClass();

    String errorMessage = renderer.renderErrorMessage();

    String labelStr = renderer.generateLabel();

    String helpHTML = renderer.renderHelpText();

    String aria = renderer.renderAriaDescribedBy();


    String inputHtml =
        "<select tabindex=\"" + pi.getTabIndex() + "\"  name=\"" + pi.getNameOfInput() + "\"" + aria
            + ">" + buildEntries(pi.getElementResult().getValue(), entries) + "</select>";;

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();

  }

  private String buildEntries(String value, List<SelectInputEntry> entries2) {
    StringBuilder inputTag = new StringBuilder();
    entries2.forEach(entry -> {
      String sel = "";
      if (value != null && value.equals(entry.getKey())) {
        sel = " SELECTED ";
      }
      inputTag.append("<option value=\"" + entry.getKey() + "\"" + sel + ">" + entry.getValue()
          + "</option>\n");
    });
    return inputTag.toString();
  }

}
