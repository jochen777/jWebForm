package jwebform;

import java.util.List;
import java.util.Map;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.env.Env;
import jwebform.processors.FormResultBuilder;
import jwebform.processors.Processor;

// Represents a form
public final class Form {

  private final String id;
  private final FormResultBuilder formResultBuilder;

  private final GroupType group;

  public Form(String id, GroupType group, FormResultBuilder formResultBuilder) {
    this.id = id;
    this.formResultBuilder = formResultBuilder;
    this.group = group;
  }


  // process each element, run validations
  public final FormResult run(Env env) {
    Processor p = new Processor();
    Map<ElementContainer, ElementResult> result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result));
  }



  public final List<ElementContainer> getElements() {
    return group.getChilds();
  }

  public final String getId() {
    return id;
  }



}
