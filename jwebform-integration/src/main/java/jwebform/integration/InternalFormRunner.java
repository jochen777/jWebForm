package jwebform.integration;

import java.util.function.BiConsumer;
import jwebform.Form;
import jwebform.FormModel.Method;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.integration.bean2form.FormResultWithBean;
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
      formResultBuilder = (a, b, c, d, e) -> new FormResultWithBean(a, b, c, d, e, input);
    }
    FormResult fr = form.run(env, formResultBuilder, formRunnerConfig.formModelBuilder);
    // RFE: What can we do, if we have more than one Form on the page?
    model.accept(formRunnerConfig.templateName, fr);

    return fr;
  }

}
