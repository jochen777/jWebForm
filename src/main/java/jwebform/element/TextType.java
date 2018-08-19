package jwebform.element;

import jwebform.element.helper.OneValueElementProcessor;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements SingleType {

  final public OneValueElementProcessor oneValueElement;

  public TextType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
  }

  public TextType(String name, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- text -->");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
