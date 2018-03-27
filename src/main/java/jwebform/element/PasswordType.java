package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class PasswordType implements SingleType {

  public final OneValueElementProcessor oneValueElement;

  public PasswordType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name,"");
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, getDefault());
  }

  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("password", pi,
        pi.getDecoration(), "");
  }


  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
