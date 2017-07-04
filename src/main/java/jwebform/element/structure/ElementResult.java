package jwebform.element.structure;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

	private final String html;
	private final ValidationResult validationResult;		// or just boolean "valid"?
	private final String value;	// Better Object??
	private final String name;	// Element name
	
	public ElementResult(String name, String html) {
		this(name, html, ValidationResult.undefined(), "");
	}
	
	public ElementResult(String name, String html, ValidationResult vr, String value) {
		this.name = name;
		this.html = html;
		this.validationResult = vr;
		this.value = value;
	}
	

	public String getHtml() {
		return html;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
}
