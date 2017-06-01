package jwebform;

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
		form.getElements().forEach(element -> html.append(element.getHtml()));
		html.append(startEndRenderer.getEnd());
		return html.toString();
	}
	
}
