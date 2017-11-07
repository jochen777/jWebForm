package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;


// TODO: No value here!, change method!
public class UploadType implements Element {

  public final static String KEY = "jwebform.element.UploadInput";
  public final OneValueElementProcessor oneValueElement;

  public UploadType(String name, OneFieldDecoration decoration, Validator validator) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, "", validator);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this, (t) -> true);
  }


  protected HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      return renderer.generateHtml(producerInfos, oneValueElement.decoration, "file", "input");
    };
  }

  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueElement.name);
  }

}
