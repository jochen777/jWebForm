package jwebform.element;

// Decorative data for typical inputfield
public class OneFieldDecoration {

	private final String label;
	private final String helptext;
	private final String placeholder;

	public OneFieldDecoration(String label) {
		this(label, "", "");
	}

	
	public OneFieldDecoration(String label, String helptext, String placeholder) {
		this.label = label;
		this.helptext = helptext;
		this.placeholder = placeholder;
	}

	public String getLabel() {
		return label;
	}

	public String getHelptext() {
		return helptext;
	}

	public String getPlaceholder() {
		return placeholder;
	}
	
	
	
}
