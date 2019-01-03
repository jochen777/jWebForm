package jwebform.integration;

import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.resultprocessor.LoggingFormResult;

// Collection of objects that are useful for templates
public class ModelForTemplate {
  private final FormModel formModel;
  private final LoggingFormResult loggingFormResult;
  private final FormResult formResult;
  private final FormRenderer formRenderer;

  public ModelForTemplate(
    FormModel formModel, LoggingFormResult loggingFormResult, FormResult formResult, FormRenderer formRenderer) {
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

  public LoggingFormResult getLoggingFormResult() {
    return loggingFormResult;
  }

  public FormResult getFormResult() {
    return formResult;
  }

  public String getHtml() {
    return formRenderer.render(getFormResult(), FormModel.Method.POST, FormModel.Html5Validation.ON.asBoolean());
  }
}
