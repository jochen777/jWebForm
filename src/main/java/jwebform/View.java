package jwebform;

import jwebform.element.structure.Element;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.view.StartEndRenderer;

public class View {

	Form form;
	
	public View(Form form) {
		super();
		this.form = form;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer(form);	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart());
		int tabIndex = 0;
		for (Element element : form.getElements()) {
			html.append(element.getHtml(tabIndex));
			if (element instanceof TabIndexAwareElement) {
				tabIndex += ((TabIndexAwareElement) element).getTabIndexIncrement();
			}
		}
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}
