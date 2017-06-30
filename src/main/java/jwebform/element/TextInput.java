package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.Validateable;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements TabIndexAwareElement, Validateable {
	
	final private String name;
	// TBD: Does it make sense to introduce a Label class here?
	final private String label;
	
	final private String value;

	final private String helptext;
	
	final private Validator validator;
	
	final private ValidationResult validationResult;
	
	final private String placeholder;
	
	public TextInput(String name, Request request, String label, String initialValue, String helptext, String placeholder, Validator validator) {
		this.name = name;
		this.label = label;
		this.helptext = helptext;
		this.validator = validator;
		this.value = this.setupValue(request, initialValue);
		this.placeholder = placeholder;
		this.validationResult = this.validate(request);
	}


	@Override
	public String getValue() {
		return value;
	}


	@Override
	public String getHtml(int tabIndex, ValidationResult overrideValidationResult) {
		ValidationResult validationResultToWorkWith = overrideValidationResult==null?validationResult:overrideValidationResult;
		
		String errorMessage = "";
		Tag wrapper = new Tag("div", "class", "form-group");
		if (validationResultToWorkWith != ValidationResult.undefined() && validationResultToWorkWith.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-success");	
		}
		if (validationResultToWorkWith != ValidationResult.undefined() && !validationResultToWorkWith.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-error");
			errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
		}
		TagAttributes labelTagAttr = new TagAttributes("for", "form-id-"+name);
		Tag labelTag = new Tag("label", labelTagAttr, label+":");

		LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
		attrs.put("tabindex", Integer.toString(tabIndex));
		attrs.put("type", "text");
		attrs.put("name", name);
		attrs.put("value", value);
		
		if (!StringUtils.isEmpty(placeholder)) {
			attrs.put("placeholder", placeholder);
		}

		String helpHTML = "";
		if (!StringUtils.isEmpty(helptext)) {
			TagAttributes helpAttributes = new TagAttributes();
			helpAttributes.addToAttribute("id", "helpBlock-" + name);
			helpAttributes.addToAttribute("class", "help-block");
			Tag help = new Tag("span",helpAttributes, helptext);
			helpHTML = help.getComplete();
			attrs.put("aria-describedby", "helpBlock-" + name);
		}

		
		
		TagAttributes inputTagAttr = new TagAttributes(attrs);
		Tag inputTag = new Tag("input", inputTagAttr);
		
		return wrapper.getStartHtml() +errorMessage+ labelTag.getComplete() + inputTag.getStartHtml()+ helpHTML + wrapper.getEndHtml() +"\n";
	}

	@Override
	public ValidationResult getValidationResult() {
		return validationResult;
		
	}


	@Override
	public int getTabIndexIncrement() {
		return 1;
	}


	private String setupValue(Request request, String initialValue){
		if (request.getParameter(name) != null) {
			return request.getParameter(name);
		}
		return initialValue;
	}
	
	private ValidationResult validate(Request request) {
		if (request.getParameter(name) != null) {
			return validator.validate(this);
		}
		return ValidationResult.undefined();
	}


	
	
	
}
