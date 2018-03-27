package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.ForceFileuploadMethod;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;


// TODO: No value here!, change method!
public class UploadType implements SingleType, ForceFileuploadMethod {

  public final OneValueElementProcessor oneValueElement;

  public UploadType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, getDefault());
  }


  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("file", pi, pi.getDecoration(),
        "");
  }

  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueElement.name);
  }

}
