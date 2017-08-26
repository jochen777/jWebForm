package jwebform;

import java.util.Map;

import jwebform.element.renderer.bootstrap.BootstrapTheme;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.view.StartEndRenderer;

public class View {

	private final Form form;
	private final Map<Element, ElementResult> elementResults;
	
	public View(Form form, Map<Element, ElementResult> elementResults) {
		this.form = form;
		this.elementResults = elementResults;
	}

	public String getHtml(BootstrapTheme theme) {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		int tabIndex = 1;
		for (Map.Entry<Element, ElementResult> entry : elementResults.entrySet()) {
			ElementResult elementResult = entry.getValue();
			ProducerInfos pi = new ProducerInfos(form.getId(), tabIndex, 
				    elementResult.getValidationResult(), elementResult.getChilds(), 
				    elementResult.getSource(), elementResult.getName(), elementResult.getValue(), theme);
			String renderedHtml = elementResult.getHtml(pi); 
			html.append(renderedHtml);
			tabIndex += elementResult.getTabIndexIncrement();
		}
		
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}
