package jwebform.element.structure;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import jwebform.validation.ValidationResult;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class StandardElementRenderer {

  public static Map<String, String> EMPTY_MAP = new HashMap<String, String>();

  public String generateHtml(
      ProducerInfos producerInfos,
      OneFieldDecoration decoration,
      String type,
      String tagname) {
    String errorMessage = generateErrorMessage(producerInfos);
    Tag inputTag = generateInputTag(producerInfos, type, tagname);
    String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();
    return html;
  }

  public String generateHtmlWithSomethingBetween(
      ProducerInfos producerInfos,
      OneFieldDecoration decoration,
      String type,
      String tagname,
      Supplier<String> betweenSupplier) {
    String errorMessage = generateErrorMessage(producerInfos);
    // TODO: This is simply wrong!
    Tag inputTag = generateInputTag(producerInfos, "input", "radio");
    String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml()
        + betweenSupplier.get() + inputTag.getEndHtml();
    return html;
  }



  public String generateErrorMessage(ProducerInfos producerInfos) {
    if (producerInfos.getElementResult().getValidationResult().isValid()
        || producerInfos.getElementResult().getValidationResult() == ValidationResult.undefined()) {
      return "";
    }
    String errorMessage =
        "Problem: " + producerInfos.getElementResult().getValidationResult().getMessage() + "<br>";
    return errorMessage;
  }

  public Tag generateInputTag(ProducerInfos producerInfos, String type, String tagName) {
    return generateInputTag(producerInfos, type, tagName, TagAttributes.empty());
  }

  public Tag generateInputTag(
      ProducerInfos producerInfos,
      String type,
      String tagName,
      TagAttributes additional) {
    LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
    attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
    attrs.put("type", type);
    attrs.put("name", producerInfos.getNameOfInput());
    attrs.put("value", producerInfos.getElementResult().getValue());
    TagAttributes inputTagAttr = new TagAttributes(attrs);
    inputTagAttr.add(additional);
    Tag inputTag = new Tag(tagName, inputTagAttr);
    return inputTag;
  }


}
