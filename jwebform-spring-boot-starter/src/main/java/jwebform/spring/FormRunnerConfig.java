package jwebform.spring;

import jwebform.integration.Bean2Form;
import jwebform.model.FormModelBuilder;
import jwebform.themes.FormRenderer;
import org.springframework.beans.factory.annotation.Autowired;

// collects different settings for the formRunner
public class FormRunnerConfig {
  public final FormRenderer formRenderer;

  public final Bean2Form bean2Form;

  public final FormModelBuilder formModelBuilder;

  public JWebFormProperties properties;

  public FormRunnerConfig(
    FormRenderer formRenderer,
    Bean2Form bean2Form,
    FormModelBuilder formModelBuilder,
    JWebFormProperties properties) {
    this.formRenderer = formRenderer;
    this.bean2Form = bean2Form;
    this.formModelBuilder = formModelBuilder;
    this.properties = properties;
  }

}
