package jwebform.element.renderer.bootstrap;

import java.util.LinkedHashMap;
import java.util.List;

import jwebform.element.TextInput;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.validation.ValidationResult;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class BootstrapTextInputRenderer implements HTMLProducer{

	
	@Override
	public String getHTML(Element inputSource, String formId, Object value, int tabIndex, ValidationResult vr, List<ElementResult> childs) {
		formId = formId + "-";
		TextInput source = (TextInput)inputSource;
		String errorMessage = "";
	      Tag wrapper = new Tag("div", "class", "form-group");
	      if (vr != ValidationResult.undefined()
	          && vr.isValid) {
	        wrapper.getTagAttributes().addToAttribute("class", " has-success");
	      }
	      if (vr != ValidationResult.undefined()
	          && !vr.isValid) {
	        wrapper.getTagAttributes().addToAttribute("class", " has-error");
	        errorMessage = "Problem: " + vr.getMessage() + "<br>";
	      }
	      TagAttributes labelTagAttr = new TagAttributes("for", formId + source.getName());
	      Tag labelTag = new Tag("label", labelTagAttr, source.getDecoration().getLabel() + ":");

	      LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
	      attrs.put("tabindex", Integer.toString(tabIndex));
	      attrs.put("type", "text");
	      attrs.put("name", formId + source.getName());
	      attrs.put("value", (String)value);

	      if (!StringUtils.isEmpty(source.getDecoration().getPlaceholder())) {
	        attrs.put("placeholder", source.getDecoration().getPlaceholder());
	      }

	      String helpHTML = "";
	      if (!StringUtils.isEmpty(source.getDecoration().getHelptext())) {
	        TagAttributes helpAttributes = new TagAttributes();
	        helpAttributes.addToAttribute("id", "helpBlock-" + source.getName());
	        helpAttributes.addToAttribute("class", "help-block");
	        Tag help = new Tag("span", helpAttributes, source.getDecoration().getHelptext());
	        helpHTML = help.getComplete();
	        attrs.put("aria-describedby", "helpBlock-" + source.getName());
	      }

	      TagAttributes inputTagAttr = new TagAttributes(attrs);
	      Tag inputTag = new Tag("input", inputTagAttr);
	      String html = wrapper.getStartHtml() + errorMessage + labelTag.getComplete()
	          + inputTag.getStartHtml() + helpHTML + wrapper.getEndHtml() + "\n";
	      return html;
	}
}
