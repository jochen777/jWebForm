package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

public final class TextType implements Element {

  public final static String KEY = "jwebform.element.TextInput";
  final public OneValueElementProcessor oneValueElement;

  public TextType(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, initialValue, validator);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this, (t) -> true);
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("text", pi, oneValueElement.decoration);
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", oneValueElement.name);
  }

}
