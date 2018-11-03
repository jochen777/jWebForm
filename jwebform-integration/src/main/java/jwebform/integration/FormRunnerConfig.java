package jwebform.integration;

import jwebform.integration.bean2form.Bean2Form;
import jwebform.model.FormModelBuilder;

// collects different settings for the formRunner
public class FormRunnerConfig {
  public final FormRenderer formRenderer;

  public final Bean2Form bean2Form;

  public final FormModelBuilder formModelBuilder;

  public String templateName;

  public FormRunnerConfig(FormRenderer formRenderer, Bean2Form bean2Form,
      FormModelBuilder formModelBuilder, String templateName) {
    this.formRenderer = formRenderer;
    this.bean2Form = bean2Form;
    this.formModelBuilder = formModelBuilder;
    this.templateName = templateName;
  }

}
