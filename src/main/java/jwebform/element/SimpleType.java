package jwebform.element;

import jwebform.element.structure.SingleType;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

// Just for demonstration!
public class SimpleType implements SingleType {

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult(producerInfos -> "simple\n");
  }

}
