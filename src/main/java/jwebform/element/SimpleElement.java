package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SimpleElement implements Element {

  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    return new ElementResult("simple", producerInfos -> "simple\n");
  }

}
