package jwebform.integration;

import java.util.function.BiConsumer;
import jwebform.Form;
import jwebform.FormModel.Method;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.integration.bean2form.FormResultWithBean;
import jwebform.model.FormModelBuilder;
import jwebform.processor.FormGenerator;
import jwebform.processor.FormResultBuilder;

class InternalFormRunner {


  public static FormResultBuilder standardFormResultBuidler = FormResult::new;

  public FormResult run(Object input, Env env, BiConsumer<String, Object> model,
      FormRunnerConfig formRunnerConfig) {
    Form form = null;
    FormResultBuilder formResultBuilder = standardFormResultBuidler;
    if (input instanceof FormGenerator) {
      form = ((FormGenerator) input).generateForm();
    } else if (input instanceof Form) {
      form = (Form) input;
    } else {
      form = formRunnerConfig.bean2Form.getFormFromBean(input);
      formResultBuilder = (a, b, c, d) -> new FormResultWithBean(a, b, c, d, formRunnerConfig.formModelBuilder, input);
    }
    FormResult fr = form.run(env, formResultBuilder);
    // RFE: What can we do, if we have more than one Form on the page?
    model.accept(formRunnerConfig.templateName, fr);

    return fr;
  }

  public FormResult runWithBFormGenerator(FormGenerator formGenerator, Env env, BiConsumer<String, Object> model,
    FormRunnerConfig formRunnerConfig){
    Form form = formGenerator.generateForm();
    FormResultBuilder formResultBuilder = standardFormResultBuidler;
    return runInternal(form, env, formResultBuilder, formRunnerConfig, model);
  }


  public FormResultWithBean runWithBean(Object input, Env env, BiConsumer<String, Object> model,
    FormRunnerConfig formRunnerConfig){
    Form form = formRunnerConfig.bean2Form.getFormFromBean(input);
    FormResultBuilder formResultBuilder = (a, b, c, d) -> new FormResultWithBean(a, b, c, d,
      formRunnerConfig.formModelBuilder, input);
    return (FormResultWithBean) runInternal(form, env, formResultBuilder, formRunnerConfig, model);
  }

  private FormResult runInternal(Form form, Env env, FormResultBuilder formResultBuilder,
    FormRunnerConfig formRunnerConfig, BiConsumer<String, Object> model) {
    FormResult fr = form.run(env, formResultBuilder);
    model.accept(formRunnerConfig.templateName, fr);
    return fr;
  }


}
