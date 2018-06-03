package jwebform.processors;

import jwebform.FormResult;

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

  FormResult build(String formId, ElementResults elementResults, boolean formIsValid);

}
