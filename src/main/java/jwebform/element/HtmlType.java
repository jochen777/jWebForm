package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HtmlType implements Element {

  public final String html;

  public HtmlType(String html) {
    this.html = html;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    // Beware - no escaping!!
    return new ElementResult(t -> html);
  }

}
