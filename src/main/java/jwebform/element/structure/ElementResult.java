package jwebform.element.structure;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

	private final ValidationResult validationResult;		// or just boolean "valid"?
	private final String value;	// Better Object??
	private final String name;	// Element name
	private final HTMLProducer htmlProducer;
	
	public ElementResult(String name, HTMLProducer htmlProducer) {
		this(name, htmlProducer, ValidationResult.ok(), "");
	}
	
	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr, String value) {
		this.name = name;
		this.htmlProducer = htmlProducer;
		this.validationResult = vr;
		this.value = value;
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

	public HTMLProducer getHtmlProducer() {
		return htmlProducer;
	}
	
}
