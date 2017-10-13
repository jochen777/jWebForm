package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.StringUtils;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements Element {

	public final static String KEY = "jwebform.element.TextInput";

	final private String name;

	final private String initialValue;

	final private Validator validator;

	final public OneFieldDecoration decoration;

	public TextInput(String name, OneFieldDecoration decoration, String initialValue, Validator validator) {
		this.name = name;
		this.validator = validator;
		this.initialValue = initialValue;
		this.decoration = decoration;
	}

	@Override
	public ElementResult prepare(PrepareInfos renderInfos) {
		String formId = renderInfos.getFormId() + "-";
		String requestVal = renderInfos.getEnv().getRequest().getParameter(formId + name);
		String value = this.fetchValue(requestVal, initialValue, formId);
		ValidationResult vr = this.validate(requestVal, value, formId);
		
		return new ElementResult(vr, value, new StaticElementInfo(name, getDefault(), 1, KEY), this);

	}

	private String fetchValue(String requestVal, String initialValue, String formId) {
		if (formSubmitted(requestVal)) {
			return requestVal;
		}
		return initialValue;
	}

	private ValidationResult validate(String requestVal, String value, String formId) {
		if (formSubmitted(requestVal)) {
			return validator.validate(value);
		}
		return ValidationResult.undefined();
	}

	private boolean formSubmitted(String requestVal) {
		return requestVal != null;
	}

	@Override
	public String toString() {
		return String.format("TextInput. name=%s", name);
	}


	// very simple version!
	public HTMLProducer getDefault() {
		return producerInfos -> {
			String errorMessage = "Problem: " + producerInfos.getElementResult().getValidationResult().getMessage()
					+ "<br>";
			LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
			attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
			attrs.put("type", "text");
			attrs.put("name", producerInfos.getFormId() + "-" + name);
			attrs.put("value", producerInfos.getElementResult().getValue());
			TagAttributes inputTagAttr = new TagAttributes(attrs);
			Tag inputTag = new Tag("input", inputTagAttr);
			String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();

			return html;
		};
	}

}
