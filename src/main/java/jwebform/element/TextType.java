package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements Element {

  public final static String KEY = "jwebform.element.TextInput";
  final public OneValueElementProcessor oneValueElement;

  public TextType(String name, Decoration decoration, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, initialValue);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault());
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("text", pi, oneValueElement.decoration,
        "");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
