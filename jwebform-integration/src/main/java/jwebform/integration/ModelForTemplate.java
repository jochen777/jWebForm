package jwebform.integration;

import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.resultprocessor.FormLogger;

// Collection of objects that are useful for templates
public class ModelForTemplate {
  private final FormModel formModel;
  private final FormLogger loggingFormResult;
  private final FormResult formResult;
  private final FormRenderer formRenderer;

  public ModelForTemplate(
    FormModel formModel, FormLogger loggingFormResult, FormResult formResult, FormRenderer formRenderer) {
    this.formModel = formModel;
    this.loggingFormResult = loggingFormResult;
    this.formResult = formResult;
    this.formRenderer = formRenderer;
  }

  public FormModel getFormModel() {
    return formModel;
  }

  public FormRenderer getFormRenderer() {
    return formRenderer;
  }

  public FormLogger getLoggingFormResult() {
    return loggingFormResult;
  }

  public FormResult getFormResult() {
    return formResult;
  }

  public String getHtml() {
    return formRenderer.render(getFormResult(), FormModel.Method.POST, FormModel.Html5Validation.ON.asBoolean());
  }
}
