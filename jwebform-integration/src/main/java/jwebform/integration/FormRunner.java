package jwebform.integration;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.*;
import jwebform.processor.FormGenerator;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform
// objects
// typically this will be filled by the framework
public class FormRunner {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormRunnerConfig formRunnerConfig;
  private final InternalFormRunner internalFormRunner = new InternalFormRunner();

  public FormRunner(Request request, SessionGet sessionGet, SessionSet sessionSet,
      BiConsumer<String, Object> model, FormRunnerConfig formRunnerConfig) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.formRunnerConfig = formRunnerConfig;
  }

  public FormResultAndBean runWithBean(Object bean) {
    FormResult fr = internalFormRunner.runWithBean(bean, env, model, formRunnerConfig);
    return new FormResultAndBean(fr, bean);

  }

  public FormResult runWitFormGenerator(FormGenerator formGenerator) {
    return internalFormRunner.runWithBFormGenerator(formGenerator, env, model, formRunnerConfig);
  }

 
  public FormResult runWithFormSupplier(Supplier<Form> formSupplier) {
    return internalFormRunner.runWithForm(formSupplier.get(), env, formRunnerConfig, model);
  }


  @Deprecated // Use either runWithBean or runWithFormSupplier
  public FormResult run(Object formOrBean) {
    return internalFormRunner.run(formOrBean, env, model, formRunnerConfig);
  }


}
