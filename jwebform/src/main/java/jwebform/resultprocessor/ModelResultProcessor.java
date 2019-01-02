package jwebform.resultprocessor;

import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.FormResult;

/**
 * A result processor does something with a formResult.
 * Examples are creation of FormModel, rendering HTML (via themes), logging, Ajax Response Objects...
  */
public class ModelResultProcessor {

  protected final FormResult formResult;

  public ModelResultProcessor(FormResult formResult) {
    this.formResult = formResult;
  }

  public FormModel getFormModel(FormModel.Method method, Html5Validation valdiaton) {
    return new FormModel(formResult, method, valdiaton);
  }


  public FormModel getFormModel() {
    return getFormModel(FormModel.Method.POST, Html5Validation.ON);
  }


  public FormModel getFormModel(Html5Validation valdiaton) {
    return getFormModel(FormModel.Method.POST, valdiaton);
  }


  public FormModel getFormModel(FormModel.Method method) {
    return getFormModel(method, Html5Validation.ON);
  }
}
