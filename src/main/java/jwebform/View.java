package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.view.StartEndRenderer;

public class View {

	private final Form form;
	private final Map<Element, ElementResult> elementResults;
	
	public View(Form form, Map<Element, ElementResult> elementResults) {
		this.form = form;
		this.elementResults = elementResults;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		elementResults.forEach((element, elementResult)  -> {
		  String renderedHtml;
		  if (elementResult.getHtmlProducer() != null) {
		    renderedHtml = elementResult.getHtmlProducer().getHTML(elementResult.getValidationResult());
		  } else {
		    renderedHtml = elementResult.getHtml();
		  }
			html.append(renderedHtml);
		});
		
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}
