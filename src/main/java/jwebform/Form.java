package jwebform;

import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.GroupType;
import jwebform.env.Env;
import jwebform.processors.ElementResults;
import jwebform.processors.FormResultBuilder;
import jwebform.processors.Processor;

import java.util.List;

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
    ElementResults result = p.run(env.getEnvWithSumitInfo(id), group);
    return formResultBuilder.build(id, result, p.checkAllValidationResults(result));
  }



  public final List<ElementContainer> getElements() {
    return group.getChilds();
  }

  public final String getId() {
    return id;
  }



}
