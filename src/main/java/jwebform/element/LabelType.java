package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class LabelType implements Element {

  public static String KEY = "jwebform.element.LabelInput";

  public final String label;

  public LabelType(String label) {
    this.label = label;
  }


  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    return new ElementResult("_label", t -> "<label>" + label + "</label>", KEY, this);
  }


  public String getLabel() {
    return label;
  }

}
