package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ForceFileuploadMethod;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;


// TODO: No value here!, change method!
public class UploadType implements Element, ForceFileuploadMethod {

  public final static String KEY = "jwebform.element.UploadInput";
  public final OneValueElementProcessor oneValueElement;

  public UploadType(String name, OneFieldDecoration decoration) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, "");
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault());
  }


  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("file", pi, oneValueElement.decoration,
        "");
  }

  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueElement.name);
  }

}
