package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements TabIndexAwareElement {
	
	final private String name; //
	// TBD: Does it make sense to introduce a Label class here?
	
	final private String initialValue;
	
	final private Validator validator;	//
	
	final private OneFieldDecoration decoration;
	
	public TextInput(String name, OneFieldDecoration decoration, String initialValue, Validator validator) {
		this.name = name;
		this.validator = validator;
		this.initialValue = initialValue;
		this.decoration = decoration;
	}

	@Override
	public ElementResult getHtml(RenderInfos renderInfos) {
		String formId = renderInfos.getFormId() + "-";
		String value = this.setupValue(renderInfos.getEnv().getRequest(), initialValue, formId);

		ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
		ValidationResult validationResultToWorkWith = renderInfos.getOverrideValidationResult()==ValidationResult.undefined()?vr:renderInfos.getOverrideValidationResult();
		
		String errorMessage = "";
		Tag wrapper = new Tag("div", "class", "form-group");
		if (validationResultToWorkWith != ValidationResult.undefined() && validationResultToWorkWith.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-success");	
		}
		if (validationResultToWorkWith != ValidationResult.undefined() && !validationResultToWorkWith.isValid) {
			wrapper.getTagAttributes().addToAttribute("class", " has-error");
			errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
		}
		TagAttributes labelTagAttr = new TagAttributes("for", formId+name);
		Tag labelTag = new Tag("label", labelTagAttr, decoration.getLabel()+":");

		LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
		attrs.put("tabindex", Integer.toString(renderInfos.getTabIndex()));
		attrs.put("type", "text");
		attrs.put("name", formId + name);
		attrs.put("value", value);
		
		if (!StringUtils.isEmpty(decoration.getPlaceholder())) {
			attrs.put("placeholder", decoration.getPlaceholder());
		}

		String helpHTML = "";
		if (!StringUtils.isEmpty(decoration.getHelptext())) {
			TagAttributes helpAttributes = new TagAttributes();
			helpAttributes.addToAttribute("id", "helpBlock-" + name);
			helpAttributes.addToAttribute("class", "help-block");
			Tag help = new Tag("span",helpAttributes, decoration.getHelptext());
			helpHTML = help.getComplete();
			attrs.put("aria-describedby", "helpBlock-" + name);
		}

		TagAttributes inputTagAttr = new TagAttributes(attrs);
		Tag inputTag = new Tag("input", inputTagAttr);
		String html = wrapper.getStartHtml() +errorMessage+ labelTag.getComplete() + inputTag.getStartHtml()+ helpHTML + wrapper.getEndHtml() +"\n";
		ElementResult result = new ElementResult(name, html, vr, value);
		return result;
	}


	@Override
	public int getTabIndexIncrement() {
		return 1;
	}

	private String setupValue(Request request, String initialValue, String formId){
		if (request.getParameter(formId + name) != null) {
			return request.getParameter(formId+name);
		}
		return initialValue;
	}
	
	private ValidationResult validate(Request request, String value, String formId) {
		if (request.getParameter(formId+name) != null) {
			return validator.validate(value);
		}
		return ValidationResult.undefined();
	}
}
