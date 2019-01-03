package jwebform.integration;

import jwebform.integration.bean2form.Bean2Form;
import jwebform.model.FormModelBuilder;
import jwebform.resultprocessor.ModelResultProcessor;
import jwebform.resultprocessor.ResultProcessorBuilder;

// collects different settings for the formRunner
public class FormRunnerConfig {
  public final FormRenderer formRenderer;

  public final Bean2Form bean2Form;

  public final ModelResultProcessor modelResultProcessor;

  public String templateName;

  public FormRunnerConfig(FormRenderer formRenderer, Bean2Form bean2Form,
    ModelResultProcessor modelResultProcessor, String templateName) {
    this.formRenderer = formRenderer;
    this.bean2Form = bean2Form;
    this.modelResultProcessor = modelResultProcessor;
    this.templateName = templateName;
  }

}
