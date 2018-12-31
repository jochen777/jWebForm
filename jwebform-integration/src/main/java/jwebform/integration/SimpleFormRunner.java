package jwebform.integration;

import java.util.Map;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.processor.FormGenerator;
import jwebform.processor.FormResultBuilder;

public class SimpleFormRunner {
  private SimpleFormRunner() {
    // prevent instanciating this!
  }


  public static FormResult run(FormGenerator generator, Map<String, String> params) {
    return SimpleFormRunner.run(generator, params, FormResult::new);
  }

  public static FormResult run(FormGenerator generator, Map<String, String> params,
      FormResultBuilder formResultBuilder) {
    return generator.generateForm().run(new EnvBuilder().of(params::get), formResultBuilder);
  }
}
