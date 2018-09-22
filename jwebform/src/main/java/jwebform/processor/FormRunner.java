package jwebform.processor;

import jwebform.FormResult;
import jwebform.env.EnvBuilder;

import java.util.Map;

public class FormRunner {
  public static FormResult run(FormGenerator generator, Map<String, String> params) {
    return generator.generateForm().run(new EnvBuilder().of(t -> params.get(t)));
  }
}
