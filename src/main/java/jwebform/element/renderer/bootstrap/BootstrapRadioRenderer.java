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

    return pi.getTheme().getRenderer().renderInputFree(renderInputs(pi, entries), pi, decoration);
  }

  private String renderInputs(ProducerInfos pi, List<RadioInputEntry> entries) {
    StringBuffer inputHtml = new StringBuffer();
    entries.forEach(radioEntriy -> inputHtml
        .append(getInputTag(radioEntriy, pi.getNameOfInput(), pi.getElementResult().getValue())));
    return inputHtml.toString();
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
