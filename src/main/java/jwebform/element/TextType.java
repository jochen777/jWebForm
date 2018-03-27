package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements SingleType {

  final public OneValueElementProcessor oneValueElement;

  public TextType(String name, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, getDefault());
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("text", pi, pi.getDecoration(),
        "");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
