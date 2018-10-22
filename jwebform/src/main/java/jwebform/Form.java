package jwebform;

import java.util.List;
import jwebform.env.Env;
import jwebform.field.structure.Field;
import jwebform.field.structure.GroupFieldType;
import jwebform.processor.FieldResults;
import jwebform.processor.FormResultBuilder;
import jwebform.processor.Processor;
import jwebform.view.ViewBuilder;

/**
 * Represents a form Holds Fields and a formId - and can be "run"
 */
public final class Form {

  private final String id;
  private final FormResultBuilder formResultBuilder;

  private final GroupFieldType group;

  private final ViewBuilder viewGenerator;

  public Form(String id, GroupFieldType group, FormResultBuilder formResultBuilder,
      ViewBuilder viewGenerator) {
    this.id = id;
    this.formResultBuilder = formResultBuilder;
    this.group = group;
    this.viewGenerator = viewGenerator;
  }


  // process each fieldType, run validations
  public final FormResult run(Env env) {
    Processor p = new Processor();
    FieldResults result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result), viewGenerator);
  }



  public final List<Field> getFields() {
    return group.getChilds();
  }

  public final String getId() {
    return id;
  }



}
