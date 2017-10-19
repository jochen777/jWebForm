package jwebform.element.structure;

import java.util.LinkedHashMap;

import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class StandardElementRenderer {
  public String generateErrorMessage(ProducerInfos producerInfos) {
    String errorMessage =
        "Problem: " + producerInfos.getElementResult().getValidationResult().getMessage() + "<br>";
    return errorMessage;
  }

  public Tag generateInputTag(ProducerInfos producerInfos, String type, String tagName) {
    LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
    attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
    attrs.put("type", type);
    attrs.put("name", producerInfos.getNameOfInput());
    attrs.put("value", producerInfos.getElementResult().getValue());
    TagAttributes inputTagAttr = new TagAttributes(attrs);
    Tag inputTag = new Tag(tagName, inputTagAttr);
    return inputTag;
  }


}
