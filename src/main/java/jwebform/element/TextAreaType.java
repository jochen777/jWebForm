package jwebform.element;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;

public class TextAreaType implements Element {

  public final static String KEY = "jwebform.element.TextAreaInput";

  public final OneValueElementProcessor oneValueElement;

  public TextAreaType(String name, Decoration decoration, String initialValue) {
    oneValueElement = new OneValueElementProcessor(name, decoration, initialValue);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault());
  }

  public HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInputComplex("textarea",
        Escape.html(pi.getElementResult().getValue()), pi, oneValueElement.decoration);
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueElement.name);
  }

}
