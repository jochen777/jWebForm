package jwebform.view;

// represents a HTML Tag
public class Tag {
	private String name;
	private TagAttributes tagAttributes;
	
	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, TagAttributes tagAttributes) {
		this(name);
		this.tagAttributes = tagAttributes;
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
	
	
}
