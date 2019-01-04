package jwebform.resultprocessor;

import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.FormResult;

public class ModelResultProcessor implements ResultProcessor<FormModel>{

  // RFE: Maybe merge this with FormModel?!?

  private final FormModel.Method method;
  private final Html5Validation valdiaton;

  public ModelResultProcessor() {
    this(FormModel.Method.POST, Html5Validation.ON);
  }

  public ModelResultProcessor(FormModel.Method method, Html5Validation valdiaton) {
    this.method = method;
    this.valdiaton = valdiaton;
  }


  @Override public FormModel process(FormResult formResult) {
    return new FormModel(formResult, method, valdiaton);
  }
}
