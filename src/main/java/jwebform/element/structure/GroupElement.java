package jwebform.element.structure;

import java.util.List;
import java.util.Map;

import jwebform.validation.FormValidator;

// a group of elements. this is basically a form.
/**
 * Needs: List of ElementContainer, FormValidator. Produces: List of ElementResults. TODO: Do we
 * need a GroupFormResult?
 *
 */
public interface GroupElement extends Element {
  public List<ElementContainer> getChilds();

  public ElementResult process(Map<ElementContainer, ElementResult> childResults);

  public List<FormValidator> getValidators(ElementContainer source);
}
