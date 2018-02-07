package jwebform.element.structure;

import java.util.List;

import jwebform.validation.Validator;

// a group of elements. this is basically a form.
/**
 * Needs: List of ElementContainer, FormValidator. Produces: List of ElementResults. TODO: Do we
 * need a GroupFormResult?
 *
 */
public interface GroupElement extends Element {
  public List<Element> getChilds();

  public Validator getValidator();
}
