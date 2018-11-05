package jwebform.spring.internal;

import java.util.function.BiConsumer;

import jwebform.Form;
import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.FormModel.Method;
import jwebform.env.Env;
import jwebform.integration.Bean2Form;
import jwebform.integration.FormResultWithBean;
import jwebform.processor.FormGenerator;
import jwebform.processor.FormResultBuilder;
import jwebform.spring.FormRunnerConfig;
import jwebform.spring.JWebFormProperties;
import jwebform.themes.FormRenderer;
import jwebform.themes.common.MessageSource;

public class InternalFormRunner {


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
      form = formRunnerConfig.bean2Form
          .getFormFromBean(input);
      formResultBuilder = (a,b,c,d, e) -> new FormResultWithBean(a,b,c,d,e, input);
    }
    FormResult fr = form.run(env, formResultBuilder, formRunnerConfig.formModelBuilder);
    // RFE: What can we do, if we have more than one Form on the page?
    model.accept(formRunnerConfig.properties.getTemplateName(), fr);
    // TODO: Must be configuraable
    model.accept(formRunnerConfig.properties.getTemplateName() + "_rendered",
        new LazyHTMLRenderer(formRunnerConfig.formRenderer, fr, Method.POST, true /* html5Validation */, msg -> msg));

    return fr;
  }

  // this "in the middle" object to allow rendering the httml just in case your really need it
  public class LazyHTMLRenderer {
    private FormRenderer fr;
    private FormResult result;
    private Method method;
    private boolean html5Validation;
    private MessageSource messageSource;



    public LazyHTMLRenderer(FormRenderer fr, FormResult result, Method method,
        boolean html5Validation, MessageSource messageSource) {
      this.fr = fr;
      this.result = result;
      this.method = method;
      this.html5Validation = html5Validation;
      this.messageSource = messageSource;
    }



    public String getHtml() {
      return fr.render(result, method, html5Validation, messageSource);
    }
  }

}
