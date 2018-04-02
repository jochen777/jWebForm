package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SubmitType implements SingleType {

  public final String label;
  public final String name;

  public SubmitType(String label) {
    this(label, "submit");
  }

  public SubmitType(String label, String name) {
    this.label = label;
    this.name = name;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult(name, t -> "<!-- submit -->", 1);
  }

}
