package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextAreaType implements SingleType {

  public final OneValueElementProcessor oneValueElement;

  public TextAreaType(String name, String initialValue) {
    oneValueElement = new OneValueElementProcessor(name, initialValue);

  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- textarea -->");
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueElement.name);
  }

}
