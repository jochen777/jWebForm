package jwebform.field.structure;

import javax.annotation.Generated;

/**
 * Decorative data for typical inputfield, can be subclassed to use more data.
 */
public class Decoration {

  private final String label;
  private final String helptext;
  private final String placeholder;
  private final boolean isTranlated;

  public static final String EMPTY = "";

  @Generated("SparkTools")
  private Decoration(Builder builder) {
    this.label = builder.label;
    this.helptext = builder.helptext;
    this.placeholder = builder.placeholder;
    this.isTranlated = builder.isTranslated;
  }

  public Decoration(String label) {
    this(label, EMPTY, EMPTY);
  }

  public Decoration(String label, String helptext, String placeholder) {
    this(label, helptext, placeholder, false);
  }


  public Decoration(String label, String helptext, String placeholder, boolean isTranslated) {
    this.label = label;
    this.helptext = helptext;
    this.placeholder = placeholder;
    this.isTranlated = isTranslated;
  }

  public Decoration(String label, String helptext) {
    this(label, helptext, EMPTY);
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


  /**
   * Creates builder to build {@link Decoration}.
   * 
   * @return created builder
   */
  @Generated("SparkTools")
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder to build {@link Decoration}.
   */
  @Generated("SparkTools")
  public static final class Builder {
    private String label;
    private String helptext = EMPTY;
    private String placeholder = EMPTY;
    private boolean isTranslated = false;

    private Builder() {}

    public Builder withLabel(String label) {
      this.label = label;
      return this;
    }

    public Builder withTranslated(boolean isTranslated) {
      this.isTranslated = isTranslated;
      return this;
    }

    public Builder withHelptext(String helptext) {
      this.helptext = helptext;
      return this;
    }

    public Builder withPlaceholder(String placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public Decoration build() {
      return new Decoration(this);
    }
  }

  public boolean isTranlated() {
    return isTranlated;
  }



}
