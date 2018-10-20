package jwebform.spring;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.*;
import jwebform.integration.Bean2From;
import jwebform.processor.FormGenerator;
import org.springframework.beans.BeanUtils;

import java.util.function.BiConsumer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform objects
// typically this will be filled by the framework
public class SimpleJWebForm<T > {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;
  private final T bean;

  public SimpleJWebForm(Class<T> typeOfBean, Request request, SessionGet sessionGet, SessionSet sessionSet, BiConsumer<String, Object> model) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.bean = BeanUtils.instantiateClass(typeOfBean);
    this.formResult = run(bean);
  }



  private FormResult run(T input) {
    Form form = null;
    if (input instanceof FormGenerator) {
      form = ((FormGenerator) input).generateForm();
    } else {
      form = new Bean2From().getFormFromBean(input);
    }
    FormResult fr =  form.run(env);

    // RFE: What can we do, if we have more than one Form on the page?
    // RFE: Should be configurable!
    model.accept("form",fr.getView());

    return fr;
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
