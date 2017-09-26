package jwebform.element.structure;

import java.util.ArrayList;
import java.util.List;

import jwebform.validation.ValidationResult;

// that wat is coming out of a "run" method of an element
public class ElementResult {

	// RFE: group this to reduce number of fields.
	private final ValidationResult validationResult; // or just boolean "valid"?
	private final String value; // Better Object??
	private final String name; // Element name
	private final HTMLProducer htmlProducer;
	private final int tabIndexIncrement;
	private final String renderKey;
	private final Element source;
	private final List<ElementResult> childs;

	public static final String EMPTY_STRING = "";
	public static final List<ElementResult> NOCHILDS = new ArrayList<>();
	
	public static final Element NO_SOURCE = t -> new ElementResult("empty", i -> "");  

	// RFE: Builder, der checkt, ob Themable element übergeben wird. wenn ja,
	// muss kein producer angegeben werden. ansonsten ist nur name mandatory

	public ElementResult(String name, HTMLProducer htmlProducer) {
		this(name, htmlProducer, ValidationResult.ok(), EMPTY_STRING, 0, EMPTY_STRING, new ArrayList<>(), NO_SOURCE);
	}

	public ElementResult(String name, HTMLProducer htmlProducer, Element source) {
		this(name, htmlProducer, ValidationResult.ok(), EMPTY_STRING, 0, EMPTY_STRING, NOCHILDS, source);
	}

	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr, String value,
			int tabIndexIncrement, String renderKey) {
		this(name, htmlProducer, vr, value, tabIndexIncrement, renderKey, new ArrayList<>(), NO_SOURCE);
	}

	public ElementResult(String name, HTMLProducer htmlProducer, ValidationResult vr, String value,
			int tabIndexIncrement, String renderKey, List<ElementResult> childs, Element source) {
		this.name = name;
		this.htmlProducer = htmlProducer;
		this.validationResult = vr;
		this.value = value;
		this.tabIndexIncrement = tabIndexIncrement;
		this.source = source;
		this.renderKey = renderKey;
		this.childs = childs;
	}

	public ElementResult cloneWithNewValidationResult(ValidationResult newValidationResult) {
		return new ElementResult(name, htmlProducer, newValidationResult, value, tabIndexIncrement, renderKey, childs,
				source);
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

	public String getRenderKey() {
		return renderKey;
	}

	public List<ElementResult> getChilds() {
		return childs;
	}

	public Element getSource() {
		return source;
	}

	public String getHtml(ProducerInfos pi) {
		HTMLProducer producer = htmlProducer;
		if (pi.getElementResult().getSource() instanceof Themable) {
			Themable element = (Themable) pi.getElementResult().getSource();
			producer = pi.getTheme().getProducer(element);
		}
		return producer.getHTML(pi);
	}

}
