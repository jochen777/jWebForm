package jwebform.integration;

import jwebform.FormResult;
import jwebform.env.*;

import java.util.function.BiConsumer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform
// objects
// typically this will be filled by the framework
public class ContainerFormRunner<T> {

  // RFE: These are not nessessary to store! just pass them to the run method!
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;
  private final T bean;
  private final InternalFormRunner formRunner = new InternalFormRunner();
  private final FormRunnerConfig formRunnerConfig;

  public ContainerFormRunner(Class<T> typeOfBean, Request request, SessionGet sessionGet,
      SessionSet sessionSet, BiConsumer<String, Object> model, FormRunnerConfig formRunnerConfig) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    try {
      this.bean = typeOfBean.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Problem with instanciating the bean of class " + typeOfBean);
    }
    this.formRunnerConfig = formRunnerConfig;

    this.formResult = run(bean);
  }



  private FormResult run(T input) {
    return formRunner.run(input, env, model, formRunnerConfig);
  }


  public boolean isValid() {
    return formResult.isValid();
  }

  public boolean isSubmitted() {
    return formResult.isSubmitted();
  }

  public String getStringValue(String name) {
    return formResult.getStringValue(name);
  }

  public T getBean() {
    return bean;
  }

}
