package jwebform.integration;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.processor.FormGenerator;
import jwebform.resultprocessor.FormLogger;

import java.util.function.BiConsumer;

class InternalFormRunner {



  public FormResult run(Object input, Env env, BiConsumer<String, Object> model,
      FormRunnerConfig formRunnerConfig) {
    Form form = null;
    if (input instanceof FormGenerator) {
      form = ((FormGenerator) input).generateForm();
    } else if (input instanceof Form) {
      form = (Form) input;
    } else {
      form = formRunnerConfig.bean2Form.getFormFromBean(input);
    }
    FormResult fr = form.run(env);
    // RFE: What can we do, if we have more than one Form on the page?
    FormRenderer formRenderer = formRunnerConfig.formRenderer;
    ModelForTemplate modelForTemplate = new ModelForTemplate(fr.process(formRunnerConfig.modelResultProcessor),
      fr.process(new FormLogger.LoggingFormResultProcessor(System.err::print)), fr, formRenderer);
    model.accept(formRunnerConfig.templateKey, modelForTemplate);

    return fr;
  }

  public FormResult runWithBFormGenerator(FormGenerator formGenerator, Env env, BiConsumer<String, Object> model,
    FormRunnerConfig formRunnerConfig){
    Form form = formGenerator.generateForm();
    return runInternal(form, env, formRunnerConfig, model);
  }


  public FormResult runWithBean(Object input, Env env, BiConsumer<String, Object> model,
    FormRunnerConfig formRunnerConfig){
    Form form = formRunnerConfig.bean2Form.getFormFromBean(input);
    return runInternal(form, env, formRunnerConfig, model);
  }

  private FormResult runInternal(Form form, Env env,
    FormRunnerConfig formRunnerConfig, BiConsumer<String, Object> model) {
    FormResult fr = form.run(env);
    model.accept(formRunnerConfig.templateKey, fr);
    return fr;
  }


}
