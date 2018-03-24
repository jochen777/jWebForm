package jwebform.element;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.SingleType;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements SingleType {

  public final OneValueElementProcessor oneValueElement;
  public final Decoration decoration;

  public PasswordType(String name, Decoration decoration) {
    this.oneValueElement = new OneValueElementProcessor(name,"");
    this.decoration = decoration;

  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, getDefault());
  }

  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("password", pi,
        decoration, "");
  }


  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
