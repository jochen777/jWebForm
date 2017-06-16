package jwebform.view;

// represents a HTML Tag
public class Tag {
	private String name;
	private TagAttributes tagAttributes;
	private String inner;
	
	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, TagAttributes tagAttributes) {
		this(name);
		this.tagAttributes = tagAttributes;
	}

	public Tag(String name, TagAttributes tagAttributes, String inner) {
		this(name, tagAttributes);
		this.inner = inner;
	}


	public String getName() {
		return name;
	}

	public TagAttributes getTagAttributes() {
		return tagAttributes;
	}

	public String getStartHtml() {
		return String.format("<%s %s>", name, tagAttributes.renderHtml());
	}

	public String getEndHtml() {
		return String.format("</%s>", name);
	}
	
	public String getComplete() {
		// RFE: automate closing tag, if empty inner
		return getStartHtml() + inner + getEndHtml();
	}
	
}
