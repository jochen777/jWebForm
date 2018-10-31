package jwebform;

import java.util.List;
import jwebform.env.Env;
import jwebform.field.structure.Field;
import jwebform.field.structure.GroupFieldType;
import jwebform.processor.FieldResults;
import jwebform.processor.FormResultBuilder;
import jwebform.processor.Processor;
import jwebform.model.FormModelBuilder;

/**
 * Represents a form Holds Fields and a formId - and can be "run"
 */
public final class Form {

  private final String id;

  private final GroupFieldType group;
  // RFE: maybe we should inject this in the fututer too.
  private final Processor p = new Processor();


  public Form(String id, GroupFieldType group
      ) {
    this.id = id;
    this.group = group;
  }


  // process each fieldType, run validations
  public final FormResult run(Env env, FormResultBuilder formResultBuilder, FormModelBuilder formModelBuilder) {
    FieldResults result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result),
      formModelBuilder);
  }

  public final FormResult run(Env env) {
    return run(env, FormResult::new, FormModel::new);
  }



  public final List<Field> getFields() {
    return group.getChilds();
  }

  public final String getId() {
    return id;
  }



}
