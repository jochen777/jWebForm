package jwebform.field;

import jwebform.field.helper.OneValueElementProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.ForceFileuploadMethod;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;


// TODO: No value here!, change method!
public class UploadType implements SingleFieldType, ForceFileuploadMethod {

  public final OneValueElementProcessor oneValueElement;

  public UploadType(String name) {
    this.oneValueElement = new OneValueElementProcessor(name, "");
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, t -> "<!-- upload -->");
  }



  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueElement.name);
  }

}
