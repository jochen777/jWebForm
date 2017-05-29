package jwebform.view;

public class StartEndRenderer {
	
	public String getStart(Model startModel) {
		TagAttributes attribs = new TagAttributes();
		attribs.addToAttribute("name", "FORMCHECKER_" + startModel.get("id"));
		attribs.addToAttribute("method", startModel.getString("method"));
		attribs.addToAttribute("id", startModel.getString("id"));
		if (((Boolean) startModel.get("html5validate")).booleanValue() == true) {
			attribs.addToAttribute("novalidate", "");	
		}
		Tag startTag = new Tag("form", attribs);
		return startTag.getStartHtml();
	}
	
	public String getEnd(Model endModel) {
		Tag startTag = new Tag("form");
		return startTag.getEndHtml();
	}
}
