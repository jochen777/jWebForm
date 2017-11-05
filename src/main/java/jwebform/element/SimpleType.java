package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SimpleType implements Element {

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult(producerInfos -> "simple\n");
  }

}
