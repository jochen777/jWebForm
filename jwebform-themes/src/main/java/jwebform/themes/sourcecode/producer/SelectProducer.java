package jwebform.themes.sourcecode.producer;

import jwebform.field.SelectType;
import jwebform.field.structure.HTMLProducer;
import jwebform.themes.sourcecode.Theme;
import jwebform.view.ProducerInfos;

import java.util.List;

public class SelectProducer implements HTMLProducer {

  private final Theme theme;

  public SelectProducer(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String getHTML(ProducerInfos pi) {
    SelectType type = (SelectType) pi.getType();
    return theme.getRenderer().renderInputComplex("select",
        buildEntries(pi.getValue(), type.entries, type.groups), pi, pi.getDecoration());
  }

  private String buildEntries(String value, List<SelectType.SelectInputEntry> entries,
      List<SelectType.SelectInputEntryGroup> groups) {
    StringBuilder inputTag = new StringBuilder();

    if (!groups.isEmpty()) {
      groups.forEach(group -> {
        inputTag.append("<optgroup label=\"" + group.getLabel() + "\">");
        buildEntries(inputTag, group.getEntries(), value);
        inputTag.append("</optgroup>");
      });
    } else {
      buildEntries(inputTag, entries, value);
    }
    return inputTag.toString();
  }

  private void buildEntries(StringBuilder inputTag, List<SelectType.SelectInputEntry> entries2,
      String inputValue) {
    entries2.forEach(entry -> {
      String sel = "";
      if (entry.getKey().equals(inputValue)) {
        sel = " SELECTED ";
      }
      inputTag.append("<option value=\"" + entry.getKey() + "\"" + sel + ">" + entry.getValue()
          + "</option>\n");
    });
  }


}
