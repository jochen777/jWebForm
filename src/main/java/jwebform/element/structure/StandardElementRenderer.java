package jwebform.element.structure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class StandardElementRenderer {
	
  public static 	Map<String, String> EMPTY_MAP = new HashMap<String, String>();
	
  public String generateErrorMessage(ProducerInfos producerInfos) {
    String errorMessage =
        "Problem: " + producerInfos.getElementResult().getValidationResult().getMessage() + "<br>";
    return errorMessage;
  }

  public Tag generateInputTag(ProducerInfos producerInfos, String type, String tagName) {
	  return generateInputTag(producerInfos, type, tagName, EMPTY_MAP);
  }
  
  public Tag generateInputTag(ProducerInfos producerInfos, String type, String tagName, Map<String, String> additional) {
    LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
    attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
    attrs.put("type", type);
    attrs.put("name", producerInfos.getNameOfInput());
    attrs.put("value", producerInfos.getElementResult().getValue());
    attrs.putAll(additional);
    TagAttributes inputTagAttr = new TagAttributes(attrs);
    Tag inputTag = new Tag(tagName, inputTagAttr);
    return inputTag;
  }


}
