package jwebform.resultprocessor;

import jwebform.FormResult;

// Interface that allows to build a ResultProcessor. Can be used to pass different implementations to the run method
@FunctionalInterface
public interface ResultProcessorBuilder {

  ModelResultProcessor build(FormResult formResult);
}
