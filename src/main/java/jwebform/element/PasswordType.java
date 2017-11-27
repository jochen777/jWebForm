package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements Element {

  public final static String KEY = "jwebform.element.PasswordInput";
  public final OneValueElementProcessor oneValueElement;


  public PasswordType(String name, OneFieldDecoration decoration) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, "");

  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault());
  }

  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("password", pi,
        oneValueElement.decoration, "");
  }


  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
