package jwebform.validation.criteria;

import jwebform.validation.Criterion;

/**
 * Checks if string is equal to one of the passed in strings.
 * 
 * Based on work of armandino (at) gmail.com
 */
public final class AcceptString extends Accept {
  private boolean caseSensitive = true;

  AcceptString(String... values) {
    super(values);
  }

  protected boolean areEqual(String v1, String v2) {
    if (caseSensitive)
      return super.areEqual(v1, v2);

    return v1.equalsIgnoreCase(v2);
  }

  /**
   * Specifies string comparison to be case-insensitive.
   */
  public Criterion ignoreCase() {
    caseSensitive = false;
    return this;
  }

}
