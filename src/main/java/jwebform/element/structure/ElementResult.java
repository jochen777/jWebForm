package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

	// RFE: group this to reduce number of fields.
	private final ValidationResult validationResult; // not static
	private final String value; // not static
	
	private final StaticElementInfo staticElementInfo;
	private final Element source; // static
	private final List<ElementResult> childs; // static

	public static final String EMPTY_STRING = "";
	public static final List<ElementResult> NOCHILDS = new ArrayList<>();
	
	public static final Element NO_SOURCE = t -> new ElementResult(null, null, null, null, null);  	// TODO: Clean this

	// RFE: Builder, der checkt, ob Themable element übergeben wird. wenn ja,
	// muss kein producer angegeben werden. ansonsten ist nur name mandatory

	// complete
	public ElementResult(ValidationResult vr, String value,
			StaticElementInfo staticElementInfo,  List<ElementResult> childs, Element source) {
		this.validationResult = vr;
		this.value = value;
		this.source = source;
		this.childs = childs;
		this.staticElementInfo = staticElementInfo;
	}

	// very simple
	public ElementResult(String name, HTMLProducer htmlProducer) {
		this(name, htmlProducer, ValidationResult.ok());
	}

	// simple with validation
	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr) {
		this(vr, EMPTY_STRING,  new StaticElementInfo(name, htmlProducer, 0, EMPTY_STRING), NOCHILDS, NO_SOURCE);
	}

	
	// simple with themable
	public ElementResult(String name, HTMLProducer htmlProducer, String renderkey, Element source) {
		this(ValidationResult.ok(), EMPTY_STRING,  new StaticElementInfo(name, htmlProducer, 1, renderkey), NOCHILDS, source);
	}

	// completeWithout Childs
	public ElementResult(ValidationResult vr, String value,
			StaticElementInfo staticElementInfo, Element source) {
		this(vr, value, staticElementInfo, NOCHILDS, source);
	}

	
	
	public ElementResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
		return new ElementResult(newValidationResult, value, staticElementInfo, childs,
				source);
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public String getValue() {
		return value;
	}


	public List<ElementResult> getChilds() {
		return childs;
	}

	public Element getSource() {
		return source;
	}

	public String getHtml(ProducerInfos pi) {
		HTMLProducer producer = staticElementInfo.getHtmlProducer();
		if (pi.getElementResult().getSource() instanceof Themable) {
			Themable element = (Themable) pi.getElementResult().getSource();
			producer = pi.getTheme().getProducer(element);
		}
		return producer.getHTML(pi);
	}

	public StaticElementInfo getStaticElementInfo() {
		return staticElementInfo;
	}

}
