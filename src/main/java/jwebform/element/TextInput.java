package jwebform.element;

import java.util.LinkedHashMap;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.Themable;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class TextInput implements Themable {

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
		String value = this.fetchValue(renderInfos.getEnv().getRequest(), initialValue, formId);
		ValidationResult vr = this.validate(renderInfos.getEnv().getRequest(), value, formId);
		return new ElementResult(name, getDefault(), vr, value, 1, KEY, ElementResult.NOCHILDS, this);
	}

	private String fetchValue(Request request, String initialValue, String formId) {
		if (formSubmitted(request, formId)) {
			return request.getParameter(formId + name);
		}
		return initialValue;
	}

	private ValidationResult validate(Request request, String value, String formId) {
		if (formSubmitted(request, formId)) {
			return validator.validate(value);
		}
		return ValidationResult.undefined();
	}

	private boolean formSubmitted(Request request, String formId) {
		return request.isSubmitted(formId + name);
	}

	@Override
	public String toString() {
		return String.format("TextInput. name=%s", name);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	// very simple version!
	@Override
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
