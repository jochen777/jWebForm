package jwebform.element.structure;

// Decorative data for typical inputfield
public class Decoration {

  private final String label;
  private final String helptext;
  private final String placeholder;
  public static final String EMPTY = "";

  public Decoration(String label) {
    this(label, EMPTY, EMPTY);
  }


  public Decoration(String label, String helptext, String placeholder) {
    this.label = label;
    this.helptext = helptext;
    this.placeholder = placeholder;
  }

  public Decoration(String label, String helptext) {
    this(label, helptext, EMPTY);
  }

  public String getLabel() {
    return label;
  }

  public boolean isLabelNotEmpty() {
    return label != null && label.length() > 0;
  }

  public String getHelptext() {
    return helptext;
  }

  public String getPlaceholder() {
    return placeholder;
  }



}
