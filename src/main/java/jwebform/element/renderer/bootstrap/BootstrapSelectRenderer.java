package jwebform.element.renderer.bootstrap;

import java.util.List;

import jwebform.element.SelectType;
import jwebform.element.SelectType.SelectInputEntry;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.view.Tag;

public class BootstrapSelectRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos producerInfos) {
    OneFieldDecoration decoration =
        ((SelectType) producerInfos.getElementResult().getSource()).decoration;
    List<SelectInputEntry> entries =
        ((SelectType) producerInfos.getElementResult().getSource()).entries;

    StandardElementRenderer renderer = new StandardElementRenderer();
    String errorMessage = renderer.generateErrorMessage(producerInfos);
    Tag inputTag = renderer.generateInputTag(producerInfos, "select", "select");
    String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml()
        + buildEntries(producerInfos.getElementResult().getValue(), entries)
        + inputTag.getEndHtml();

    return html;
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
