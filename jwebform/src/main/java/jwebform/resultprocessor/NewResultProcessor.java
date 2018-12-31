package jwebform.resultprocessor;

import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.FormResult;

public class NewResultProcessor {


  public NewResultProcessor() {}

  public FormModel getFormModel(FormResult formResult) {
    return new FormModel(formResult, FormModel.Method.POST, Html5Validation.ON);
  }

}
