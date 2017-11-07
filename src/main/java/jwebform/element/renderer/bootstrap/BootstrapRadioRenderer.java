package jwebform.element.renderer.bootstrap;

import java.util.List;

import jwebform.element.RadioType;
import jwebform.element.RadioType.RadioInputEntry;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;

public class BootstrapRadioRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration =
        ((RadioType) pi.getElementResult().getSource()).oneValueElement.decoration;
    List<RadioInputEntry> entries = ((RadioType) pi.getElementResult().getSource()).entries;

    BootstrapRenderer renderer = new BootstrapRenderer(pi, decoration);

    String errorClass = renderer.calculateErrorClass();

    String errorMessage = renderer.renderErrorMessage();

    String labelStr = renderer.generateLabel();

    String helpHTML = renderer.renderHelpText();

    StringBuffer inputHtml = new StringBuffer();
    entries.forEach(radioEntriy -> inputHtml
        .append(getInputTag(radioEntriy, pi.getNameOfInput(), pi.getElementResult().getValue())));

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();
  }

  private String getInputTag(RadioInputEntry entry, String name, String value) {
    return "<input id=\"form-radio-" + name + "-" + entry.getKey() + "\" "
        + " type=\"radio\" name=\"" + name + "\"  value=\"" + entry.getKey() + "\" "
        + getCheckedStatus(entry.getKey(), value) + "" + " " + " ><label for=\"form-radio-" + name
        + "\">" + entry.getValue() + "</label>\n";
  }

  private String getCheckedStatus(String _name, String value) {
    if (value != null && value.equals(_name)) {
      return "checked";
    } else {
      return "";
    }
  }

}
