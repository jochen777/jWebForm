package jwebform.resultprocessor;

import jwebform.FormResult;

/**
 * A result processor does something with a formResult.
 * Examples are creation of FormModel, rendering HTML (via themes), logging, Ajax Response Objects...
 */
@FunctionalInterface
public interface ResultProcessor <T> {
  T process(FormResult formResult);
}
