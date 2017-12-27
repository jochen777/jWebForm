package jwebform.element;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextType implements Element {

  public final static String KEY = "jwebform.element.TextInput";
  final public OneValueElementProcessor oneValueElement;
  private final Decoration decoration;

  public TextType(String name, Decoration decoration, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
    this.decoration = decoration;
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault());
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("text", pi, decoration,
        "");
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
