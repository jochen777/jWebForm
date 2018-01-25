package jwebform.view.theme;

import java.util.List;

import jwebform.element.RadioType.RadioInputEntry;
import jwebform.element.structure.ProducerInfos;
import jwebform.view.ElementRenderer;
import jwebform.view.RadioRenderer;

public class BootstrapRadioRenderer implements RadioRenderer {

  @Override
  public String renderInputs(ProducerInfos pi, List<RadioInputEntry> entries) {
    StringBuffer inputHtml = new StringBuffer();
    entries.forEach(radioEntriy -> inputHtml.append(getInputTag(radioEntriy, pi.getNameOfInput(),
        pi.getElementResult().getValue(), pi.getTheme().getRenderer())));
    return inputHtml.toString();
  }

  private String getInputTag(
      RadioInputEntry entry,
      String name,
      String value,
      ElementRenderer elementRenderer) {
    // return "<input id=\"form-radio-" + name + "-" + entry.getKey() + "\" "
    // + " type=\"radio\" name=\"" + name + "\" value=\"" + entry.getKey() + "\" "
    // + getCheckedStatus(entry.getKey(), value) + " >"
    // + elementRenderer.renderSimpleLabel("form-radio-" + name, entry.getValue());

    /*
     * <label> <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
     * Option one is this and that&mdash;be sure to include why it's great </label>
     */
    StringBuilder builder = new StringBuilder();
    builder.append("<div class=\"radio\"><label><input type=\"radio\" name=\"").append(name)
        .append("\" id=\"").append("form-radio-" + name + "-" + entry.getKey())
        .append("\" value=\"").append(entry.getKey()).append("\" ")
        .append(getCheckedStatus(entry.getKey(), value)).append(">").append(entry.getValue())
        .append("</label></div>");
    return builder.toString();
    // return elementRenderer.renderInput("radio", name, "form-radio-" + name + "-" +
    // entry.getKey(),
    // entry.getKey(), getCheckedStatus(entry.getKey(), value))
    // + elementRenderer.renderSimpleLabel("form-radio-" + name, entry.getValue());

  }

  private String getCheckedStatus(String _name, String value) {
    if (_name.equals(value)) {
      return " checked";
    } else {
      return "";
    }
  }

}
