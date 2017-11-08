package jwebform.element;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

public class TextAreaType implements Element {

  public final static String KEY = "jwebform.element.TextAreaInput";

  public final OneValueElementProcessor oneValueElement;

  public TextAreaType(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    oneValueElement = new OneValueElementProcessor(name, decoration, initialValue, validator);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this, (t) -> true);
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
