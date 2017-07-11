package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

	
	// RFE: group this to reduce number of fields.
	private final ValidationResult validationResult;		// or just boolean "valid"?
	private final String value;	// Better Object??
	private final String name;	// Element name
	private final HTMLProducer htmlProducer;
	private final int tabIndexIncrement;
	private final Element source;
	private final String renderKey;
	private final List<ElementResult> childs;
	
	public ElementResult(String name, HTMLProducer htmlProducer) {
		this(name, htmlProducer, ValidationResult.ok(), "", 1, null, "", new ArrayList<>());
	}
	
	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr, String value, int tabIndexIncrement, Element source, String renderKey) {
		this(name, htmlProducer, vr, value, tabIndexIncrement, source, renderKey, new ArrayList<>());
	}
	
	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr, String value, int tabIndexIncrement, Element source, String renderKey, List<ElementResult> childs) {
		this.name = name;
		this.htmlProducer = htmlProducer;
		this.validationResult = vr;
		this.value = value;
		this.tabIndexIncrement = tabIndexIncrement;
		this.source = source;
		this.renderKey = renderKey;
		this.childs = childs;
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

	public int getTabIndexIncrement() {
		return tabIndexIncrement;
	}

	public Element getSource() {
		return source;
	}

	public String getRenderKey() {
		return renderKey;
	}

	public List<ElementResult> getChilds() {
		return childs;
	}
	
}
