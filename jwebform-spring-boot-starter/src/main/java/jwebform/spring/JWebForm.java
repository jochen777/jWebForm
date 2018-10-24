package jwebform.spring;

import java.util.function.BiConsumer;
import javax.validation.Validator;
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
public class JWebForm {
  private final Env env;
  private final BiConsumer<String, Object> model;
  private final Validator validator;

  public JWebForm(Request request, SessionGet sessionGet, SessionSet sessionSet,
      BiConsumer<String, Object> model, Validator validator) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;
    this.validator = validator;
  }

  public FormResult run(Object formOrBean) {
    InternalFormRunner formRunner = new InternalFormRunner();
    return formRunner.run(formOrBean, env, model, validator);
  }


}
