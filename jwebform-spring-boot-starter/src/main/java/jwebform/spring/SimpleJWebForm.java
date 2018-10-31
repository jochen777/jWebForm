package jwebform.spring;

import java.util.function.BiConsumer;

import jwebform.integration.Bean2Form;
import org.springframework.beans.BeanUtils;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;
import jwebform.spring.internal.InternalFormRunner;
import jwebform.themes.FormRenderer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform
// objects
// typically this will be filled by the framework
public class SimpleJWebForm<T> {

  // RFE: These are not nessessary to store! just pass them to the run method!
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;
  private final T bean;
  private final InternalFormRunner formRunner = new InternalFormRunner();
  private final FormRunnerConfig formRunnerConfig;

  public SimpleJWebForm(
    Class<T> typeOfBean, Request request, SessionGet sessionGet,
      SessionSet sessionSet, BiConsumer<String, Object> model, FormRunnerConfig formRunnerConfig) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.bean = BeanUtils.instantiateClass(typeOfBean);
    this.formRunnerConfig = formRunnerConfig;

    this.formResult = run(bean);
  }



  private FormResult run(T input) {
    return formRunner.run(input, env, model, formRunnerConfig);
  }


  public boolean isOk() {
    return formResult.isOk();
  }

  public String getStringValue(String name) {
    return formResult.getStringValue(name);
  }

  public T getBean() {
    return bean;
  }

}
