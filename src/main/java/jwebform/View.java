package jwebform;

import jwebform.view.Model;
import jwebform.view.StartEndRenderer;

public class View {

	Form form;
	
	public View(Form form) {
		super();
		this.form = form;
	}

	public String getHtml() {
		StartEndRenderer startEndRenderer = new StartEndRenderer();	// RFE: Remove new
		StringBuilder html = new StringBuilder();
		html.append(startEndRenderer.getStart(this.getStart()));
		form.getElements().forEach(element -> html.append(element.getHtml()));
		html.append(startEndRenderer.getEnd(this.getEnd()));
		return html.toString();
	}
	
	public Model getStart() {
		Model startModel = new Model("id", form.getId());
		startModel.put("html5validate", true);
		// no action per default. Will always go to same url
		// startModel.put("action", "");
		// change method, if image-upload is avail!
		startModel.put("method", "POST");
		return startModel;
	}

	public Model getEnd() {
		Model endModel = new Model();
		return endModel;
	}
}
