package jwebform.element.renderer.bootstrap;

import java.util.LinkedHashMap;
import jwebform.element.TextType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class BootstrapTextInputRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration = ((TextType) pi.getElementResult().getSource()).decoration;
    String errorMessage = "";
    ValidationResult vr = pi.getElementResult().getValidationResult();
    Tag wrapper = new Tag("div", "class", "form-group");
    if (vr != ValidationResult.undefined() && vr.isValid) {
      wrapper.getTagAttributes().addToAttribute("class", " has-success");
    }
    if (vr != ValidationResult.undefined() && !vr.isValid) {
      wrapper.getTagAttributes().addToAttribute("class", " has-error");
      errorMessage = "Problem: " + vr.getMessage() + "<br>";
    }
    TagAttributes labelTagAttr = new TagAttributes("for", pi.getNameOfInput());
    Tag labelTag = new Tag("label", labelTagAttr, decoration.getLabel() + ":");

    LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
    attrs.put("tabindex", Integer.toString(pi.getTabIndex()));
    attrs.put("type", "text");
    attrs.put("name", pi.getNameOfInput());
    attrs.put("value", pi.getElementResult().getValue());

    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      attrs.put("placeholder", decoration.getPlaceholder());
    }

    String helpHTML = "";
    if (decoration.getHelptext() != OneFieldDecoration.EMPTY) {
      TagAttributes helpAttributes = new TagAttributes();
      helpAttributes.addToAttribute("id", "helpBlock-" + pi.getNameOfInput());
      helpAttributes.addToAttribute("class", "help-block");
      Tag help = new Tag("span", helpAttributes, decoration.getHelptext());
      helpHTML = help.getComplete();
      attrs.put("aria-describedby", "helpBlock-" + pi.getNameOfInput());
    }

    TagAttributes inputTagAttr = new TagAttributes(attrs);
    Tag inputTag = new Tag("input", inputTagAttr);
    String html = wrapper.getStartHtml() + errorMessage + labelTag.getComplete()
        + inputTag.getStartHtml() + helpHTML + wrapper.getEndHtml() + "\n";
    return html;
  }

}
