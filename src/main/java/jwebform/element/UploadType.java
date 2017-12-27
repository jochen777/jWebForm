package jwebform.element;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ForceFileuploadMethod;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;


// TODO: No value here!, change method!
public class UploadType implements Element, ForceFileuploadMethod {

  public final OneValueElementProcessor oneValueElement;
  private final Decoration decoration;

  public UploadType(String name, Decoration decoration) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
    this.decoration = decoration;
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, getDefault());
  }


  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("file", pi, decoration,
        "");
  }

  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueElement.name);
  }

}
