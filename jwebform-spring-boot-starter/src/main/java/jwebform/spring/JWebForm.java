package jwebform.spring;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.*;

import java.util.function.BiConsumer;

// Container, that holds a jWebForm Form (or a normal bean) and provides a facade to jwebform objects
// typically this will be filled by the framework
public class JWebForm {
  private final Env env;
  private final BiConsumer<String, Object> model;

  public JWebForm(Request request, SessionGet sessionGet, SessionSet sessionSet, BiConsumer<String, Object> model) {
    this.env = new EnvBuilder().of(request, sessionGet, sessionSet);
    this.model = model;

  }

  public FormResult run(Form form) {
    FormResult fr =  form.run(env);

    // RFE: What can we do, if we have more than one Form on the page?
    // RFE: Should be configurable!
    model.accept("form",fr.getView());

    return fr;
  }


}
