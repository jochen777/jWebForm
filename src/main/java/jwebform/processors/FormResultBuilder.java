package jwebform.processors;

import java.util.Map;

import jwebform.FormResult;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

/**
 * builds formResults. Allows to create subclassed FormResults by implementing this
 * 
 * Example: If you want a formResult that has a debug output, you can implement this interface and
 * add a debug() method.
 * 
 * Currently used to add a fillBean() method within the integration project (FormResultWithBean)
 * 
 * @author pier
 *
 */
@FunctionalInterface
public interface FormResultBuilder {

  FormResult build(
      String formId,
      Map<ElementContainer, ElementResult> elementResults,
      boolean formIsValid);

}
