package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;

public class SimpleElement implements Element {

  @Override
  public ElementResult prepare(Env env) {
    return new ElementResult("simple", producerInfos -> "simple\n");
  }

}
