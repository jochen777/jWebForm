package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SubmitType implements Element {

  public static String KEY = "jwebform.element.SubmitButton";

  public final String label;

  public SubmitType(String label) {
    this.label = label;
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return new ElementResult("submit", getDefault(), SubmitType.KEY, this);
  }

  public HTMLProducer getDefault() {
    return producerInfos -> "<input tabindex=\"" + producerInfos.getTabIndex()
        + "\" type=\"submit\" value=\"" + label + "\"><!-- own renderer -->";
  }

}
