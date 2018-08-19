package jwebform.element.structure;

import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.processors.ElementResults;
import jwebform.validation.FormValidator;

import java.util.List;

// a group of elements. this is basically a form.
/**
 * Needs: List of ElementContainer, FormValidator. Produces: List of ElementResults. TODO: Do we
 * need a GroupFormResult?
 *
 */
public interface GroupType extends FieldType {
  List<ElementContainer> getChilds();

  ElementResult process(EnvWithSubmitInfo env, ElementResults childResults);

  List<FormValidator> getValidators(ElementContainer source);
}
