package jwebform.element;

import java.util.LinkedHashMap;

import com.coverity.security.Escape;

import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.Validateable;
import jwebform.env.Request;
import jwebform.validation.Criterion;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements TabIndexAwareElement, Validateable {
	
	String name;
	// TBD: Does it make sense to introduce a Label class here?
	String label;
	
	String value;

	String helptext;
	
	int tabIndex=0;

	Validator validator;
	
	private ValidationResult validationResult = null;
	
	public TextInput(String name, String label, String initialValue, String helptext, Validator validator) {
		this.name = name;
		this.label = label;
		this.value = initialValue;
		this.helptext = helptext;
		this.validator = validator;
		
		
	}


	@Override
	public String getHtml() {
		String errorMessage = "";
		Tag wrapper = new Tag("div", "class", "form-group");
		if (validationResult != null && validationResult.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-success");	
		}
		if (validationResult != null && !validationResult.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-error");
			errorMessage = "Problem: " + validationResult.getMessage() + "<br>";
		}
		TagAttributes labelTagAttr = new TagAttributes("for", "form-id-"+name);
		Tag labelTag = new Tag("label", labelTagAttr, label+":");

		LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
		attrs.put("tabindex", Integer.toString(tabIndex));
		attrs.put("type", "text");
		attrs.put("name", name);
		attrs.put("value", Escape.html(value));
		TagAttributes inputTagAttr = new TagAttributes(attrs);
		Tag inputTag = new Tag("input", inputTagAttr);
		
		return wrapper.getStartHtml() +errorMessage+ labelTag.getComplete() + inputTag.getStartHtml()+ wrapper.getEndHtml() +"\n";
	}

	@Override
	public void overwriteValidationResult(ValidationResult vr) {
		this.validationResult = vr;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int feedTabIndex(int currentTabIndex) {
		tabIndex = currentTabIndex;
		return tabIndex;
	}

	@Override
	public ValidationResult run(Request request) {
		if (request.getParameter(name) != null) {
			this.value = request.getParameter(name);
			validationResult = validator.validate(this);
		}
		return validationResult;
		
	}
	
	
	
}
