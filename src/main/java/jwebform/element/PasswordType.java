package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

public class PasswordType implements Element {

  public final static String KEY = "jwebform.element.PasswordInput";
  public final OneValueElementProcessor oneValueElement;


  public PasswordType(String name, OneFieldDecoration decoration, Validator validator) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, "", validator);

  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this, (t) -> true);
  }

  protected HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      return renderer.generateHtml(producerInfos, oneValueElement.decoration, "password", "input");
    };
  }


  @Override
  public String toString() {
    return String.format("PasswordInput. name=%s", oneValueElement.name);
  }

}
