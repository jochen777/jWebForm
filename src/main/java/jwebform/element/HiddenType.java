package jwebform.element;

import jwebform.element.helper.OneValueElementProcessor;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HiddenType implements SingleType {

  public final String name;
  public final String value;
  final public OneValueElementProcessor oneValueElement;

  public HiddenType(String name, String initialValue) {
    this.name = name;
    this.value = initialValue;
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);

  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResultNoTabIndexIncrement(env, t -> "<!-- hidden -->");
  }

}
