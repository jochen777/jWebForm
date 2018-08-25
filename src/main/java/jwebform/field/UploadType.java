package jwebform.field;

import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.ForceFileuploadMethod;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;


// TODO: No value here!, change method!
public class UploadType implements SingleFieldType, ForceFileuploadMethod {

  public final OneValueTypeProcessor oneValueType;

  public UploadType(String name) {
    this.oneValueType = new OneValueTypeProcessor(name, "");
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueType.calculateFieldResult(env, t -> "<!-- upload -->");
  }



  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", oneValueType.name);
  }

}
