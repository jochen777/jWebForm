package jwebform.view;

import jwebform.Form;

public class StartEndRenderer {
	
	private final Form form;
	
	public StartEndRenderer(Form form){
		this.form = form;
	}
	
	public String getStart() {
		TagAttributes attribs = new TagAttributes();
		attribs.addToAttribute("name", form.getId() + "-FORMCHECKER");
		attribs.addToAttribute("method", form.getMethod());
		attribs.addToAttribute("id", form.getId());
		if (form.isHtml5Validate()) {
			attribs.addToAttribute("novalidate", "");	
		}
		Tag startTag = new Tag("form", attribs);
		return startTag.getStartHtml()+"\n";
	}
	
	public String getEnd() {
		Tag startTag = new Tag("form");
		return startTag.getEndHtml();
	}
}
