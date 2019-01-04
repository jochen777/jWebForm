package jwebform.processor;

import jwebform.FormResult;

/**
 * builds formResults. Allows to create subclassed FormResults by implementing this
 * 
 * Example: If you want a formResult that has a additional methods (for example debug), you can implement this interface and
 * add a debug() method.
 *
 * Beware: The better way is to implement a ResultProcessor
 *
 * Normally this is just a FormResult::new default method.
 * 
 * Currently used to add a fillBean() method within the integration project (FormResultWithBean)
 * 
 * @author pier
 *
 */
@FunctionalInterface
public interface FormResultBuilder {

  FormResult build(String formId, FieldResults fieldResults, boolean formIsValid, boolean isFirstRun);

}
