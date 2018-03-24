package jwebform.element;

import jwebform.element.structure.SingleType;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

// RFE: Is this useful at all? (as long as we have HTMLType)
public class LabelType implements SingleType {

  public final String label;

  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult(t -> "<label>" + label + "</label>");
  }


  public String getLabel() {
    return label;
  }

}
