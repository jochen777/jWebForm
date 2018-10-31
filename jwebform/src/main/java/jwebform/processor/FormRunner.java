package jwebform.processor;

import jwebform.FormModel;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.model.FormModelBuilder;

import java.util.Map;

public class FormRunner {
  private FormRunner() {
    // prevent instanciating this!
  }


  public static FormResult run(FormGenerator generator, Map<String, String> params) {
    return FormRunner.run(generator, params, FormResult::new, FormModel::new);
  }

  public static FormResult run(FormGenerator generator, Map<String, String> params, FormResultBuilder formResultBuilder, FormModelBuilder formModelBuilder) {
    return generator.generateForm().run(new EnvBuilder().of(params::get), formResultBuilder, formModelBuilder);
  }
}
