package jwebform.element;

import com.coverity.security.Escape;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
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
    return oneValueElement.calculateElementResult(env, getDefault());
  }

  public HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInputComplex("textarea",
        Escape.html(pi.getElementResult().getValue()), pi, pi.getDecoration());
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueElement.name);
  }

}
