package jwebform.resultprocessor;

import jwebform.FormResult;

/**
 * Class that implement this interface can ask a formResult to be processed.
 * @param <T>
 */
@FunctionalInterface
public interface ResultProcessor <T> {
  T process(FormResult formResult);
}
