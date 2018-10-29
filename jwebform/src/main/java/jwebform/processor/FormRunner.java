package jwebform.processor;

import jwebform.FormResult;
import jwebform.env.EnvBuilder;

import java.util.Map;

public class FormRunner {
  private FormRunner() {
    // prevent instanciating this!
  }
  public static FormResult run(FormGenerator generator, Map<String, String> params) {
    return generator.generateForm().run(new EnvBuilder().of(params::get));
  }
}
