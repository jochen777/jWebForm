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
    OneFieldDecoration decoration =
        ((SelectType) pi.getElementResult().getSource()).oneValueElement.decoration;
    List<SelectInputEntry> entries = ((SelectType) pi.getElementResult().getSource()).entries;

    ElementRenderer renderer = new BootstrapRenderer();
    return renderer.renderInputComplex("select",
        buildEntries(pi.getElementResult().getValue(), entries), pi, decoration);
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
