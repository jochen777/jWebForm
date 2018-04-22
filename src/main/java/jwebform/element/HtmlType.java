package jwebform.element;

import jwebform.element.structure.SingleType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HtmlType implements SingleType {

  public final String html;

  public HtmlType(String html) {
    this.html = html;
  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    // Beware - no escaping!!
    return ElementResult.builder().withStaticElementInfo(new StaticElementInfo("", t -> html, 0))
        .build();
  }

}
