package jwebform.view;

import jwebform.Form;

public class StartEndRenderer {
	
	Form form;
	
	public StartEndRenderer(Form form){
		this.form = form;
	}
	
	public String getStart() {
		TagAttributes attribs = new TagAttributes();
		attribs.addToAttribute("name", "FORMCHECKER_" + form.getId());
		attribs.addToAttribute("method", form.getMethod());
		attribs.addToAttribute("id", form.getId());
		if (form.isHtml5Validate()) {
			attribs.addToAttribute("novalidate", "");	
		}
		Tag startTag = new Tag("form", attribs);
		return startTag.getStartHtml();
	}
	
	public String getEnd() {
		Tag startTag = new Tag("form");
		return startTag.getEndHtml();
	}
}
