package jwebform.spring;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.*;
import jwebform.processor.FormGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.function.BiConsumer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform objects
// typically this will be filled by the framework
public class SimpleJWebForm<T extends FormGenerator > {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final FormResult formResult;

  public SimpleJWebForm(Class<T> typeOfBean, Request request, SessionGet sessionGet, SessionSet sessionSet, BiConsumer<String, Object> model) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.formResult = run(BeanUtils.instantiateClass(typeOfBean));
  }



  private FormResult run(T input) {
    Form f = input.generateForm();
    FormResult fr =  f.run(env);

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

}
