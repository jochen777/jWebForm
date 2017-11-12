package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

// RFE: Is this useful at all? (as long as we have HTMLType)
public class LabelType implements Element {

  public static String KEY = "jwebform.element.LabelInput";

  public final String label;

  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult(t -> "<label>" + label + "</label>", KEY);
  }


  public String getLabel() {
    return label;
  }

}
