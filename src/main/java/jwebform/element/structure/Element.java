package jwebform.element.structure;

import jwebform.env.Env.EnvWithSubmitInfo;

@FunctionalInterface
public interface Element {

  // RFE: Find better name: "process", "validate", "Prepare Model"
  public ElementResult prepare(EnvWithSubmitInfo env);

}
