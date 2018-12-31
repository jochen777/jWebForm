package jwebform.integration;

import java.util.HashMap;
import jwebform.FormResult;
import jwebform.processor.FieldResults;

/**
 * A formResult with a method, that delivers the form-Result as object that could be easyly
 * tranformed to JSON so an JS can accept that on the frontend. returns
 * 
 *
 */

public class AjaxFormResult extends FormResult {


  public AjaxFormResult(String formId, FieldResults fieldResults, boolean formIsValid,
      boolean isFirstRun) {
    super(formId, fieldResults, formIsValid, isFirstRun);
  }

  public AjaxResult getAjaxResult() {
    return new AjaxResult(formIsValid, new HashMap<String, String>());

  }



}
