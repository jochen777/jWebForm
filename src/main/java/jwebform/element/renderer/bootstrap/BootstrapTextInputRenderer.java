package jwebform.element.renderer.bootstrap;

import java.util.LinkedHashMap;

import jwebform.element.OneFieldDecoration;
import jwebform.element.TextInput;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class BootstrapTextInputRenderer implements HTMLProducer{
	
	@Override
	public String getHTML(ProducerInfos pi) {
	  OneFieldDecoration decoration = ((TextInput)pi.getSource()).decoration;
		String formId = pi.getFormId() + "-";
		String errorMessage = "";
	      Tag wrapper = new Tag("div", "class", "form-group");
	      if (pi.getVr() != ValidationResult.undefined()
	          && pi.getVr().isValid) {
	        wrapper.getTagAttributes().addToAttribute("class", " has-success");
	      }
	      if (pi.getVr() != ValidationResult.undefined()
	          && !pi.getVr().isValid) {
	        wrapper.getTagAttributes().addToAttribute("class", " has-error");
	        errorMessage = "Problem: " + pi.getVr().getMessage() + "<br>";
	      }
	      TagAttributes labelTagAttr = new TagAttributes("for", formId + pi.getName());
	      Tag labelTag = new Tag("label", labelTagAttr, decoration.getLabel() + ":");

	      LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
	      attrs.put("tabindex", Integer.toString(pi.getTabIndex()));
	      attrs.put("type", "text");
	      attrs.put("name", formId + pi.getName());
	      attrs.put("value", pi.getValue());

	      if (!StringUtils.isEmpty(decoration.getPlaceholder())) {
	        attrs.put("placeholder", decoration.getPlaceholder());
	      }

	      String helpHTML = "";
	      if (!StringUtils.isEmpty(decoration.getHelptext())) {
	        TagAttributes helpAttributes = new TagAttributes();
	        helpAttributes.addToAttribute("id", "helpBlock-" + pi.getName());
	        helpAttributes.addToAttribute("class", "help-block");
	        Tag help = new Tag("span", helpAttributes, decoration.getHelptext());
	        helpHTML = help.getComplete();
	        attrs.put("aria-describedby", "helpBlock-" + pi.getName());
	      }

	      TagAttributes inputTagAttr = new TagAttributes(attrs);
	      Tag inputTag = new Tag("input", inputTagAttr);
	      String html = wrapper.getStartHtml() + errorMessage + labelTag.getComplete()
	          + inputTag.getStartHtml() + helpHTML + wrapper.getEndHtml() + "\n";
	      return html;
	}

}
