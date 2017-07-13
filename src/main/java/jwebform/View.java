package jwebform;

import java.util.Map;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Theme;
import jwebform.view.StartEndRenderer;

public class View {

	private final Form form;
	private final Map<Element, ElementResult> elementResults;
	private final Theme theme;
	
	public View(Form form, Map<Element, ElementResult> elementResults, Theme theme) {
		this.form = form;
		this.elementResults = elementResults;
		this.theme = theme;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		int tabIndex = 1;
		for (Map.Entry<Element, ElementResult> entry : elementResults.entrySet()) {
			ElementResult elementResult = entry.getValue();
			String renderedHtml;
			HTMLProducer htmlProducer = elementResult.getHtmlProducer(); 
//			if (theme.getHtmlProducer().containsKey(elementResult.getRenderKey())) {
//				htmlProducer = theme.getHtmlProducer().get(elementResult.getRenderKey()); 
//			}
			ProducerInfos pi = new ProducerInfos(form.getId(), tabIndex, 
			    elementResult.getValidationResult(), elementResult.getChilds(), 
			    elementResult.getStaticRenderData());
		    renderedHtml = htmlProducer.getHTML(pi);
			html.append(renderedHtml);
			tabIndex += elementResult.getTabIndexIncrement();
		}
		
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}
