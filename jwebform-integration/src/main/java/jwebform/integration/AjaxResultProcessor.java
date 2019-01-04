package jwebform.integration;

import jwebform.FormResult;
import jwebform.resultprocessor.ResultProcessor;

/**
 * A formResult with a method, that delivers the form-Result as object that could be easyly
 * tranformed to JSON so an JS can accept that on the frontend. returns
 * 
 *
 */

public class AjaxResultProcessor implements ResultProcessor<AjaxResult> {

  @Override public AjaxResult process(FormResult formResult) {
    return new AjaxResult(formResult);
  }
}
