package jwebform.element.structure;

import java.util.List;

import jwebform.validation.FormValidator;

// a group of elements. this is basically a form.
/**
 * Needs: List of ElementContainer, FormValidator. Produces: List of ElementResults. TODO: Do we
 * need a GroupFormResult?
 *
 */
public interface GroupElement extends Element {
  public List<ElementContainer> getChilds();

  public List<FormValidator> getValidators();
}
