package jwebform.spring;

import java.util.function.BiConsumer;
import javax.validation.Validator;
import org.springframework.beans.BeanUtils;
import jwebform.FormResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.env.Request;
import jwebform.env.SessionGet;
import jwebform.env.SessionSet;
import jwebform.spring.internal.InternalFormRunner;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform
// objects
// typically this will be filled by the framework
public class SimpleJWebForm<T> {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;
  private final T bean;
  private final Validator validator;

  public SimpleJWebForm(Class<T> typeOfBean, Request request, SessionGet sessionGet,
      SessionSet sessionSet, BiConsumer<String, Object> model, Validator validator) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.bean = BeanUtils.instantiateClass(typeOfBean);
    this.validator = validator;
    this.formResult = run(bean);
  }



  private FormResult run(T input) {
    InternalFormRunner formRunner = new InternalFormRunner();
    return formRunner.run(input, env, model, validator);
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
