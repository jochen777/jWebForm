package jwebform.integration;

import java.util.function.BiConsumer;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;
import jwebform.processor.FormGenerator;

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


  @Deprecated // Use either runWithBean or runWithFormGenerator
  public FormResult run(Object formOrBean) {
    return internalFormRunner.run(formOrBean, env, model, formRunnerConfig);
  }

  public static class FormResultAndBean {
    private final FormResult fr;
    private final Object bean;

    public FormResultAndBean(FormResult fr, Object bean) {
      this.fr = fr;
      this.bean = bean;
    }

    public FormResult getFr() {
      return fr;
    }

    public Object getBean() {
      return bean;
    }
  }

}
