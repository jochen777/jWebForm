package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.env.Env;

public class SubmitButton implements Element {

  public static String KEY = "jwebform.element.SubmitButton";

  public final String label;

  public SubmitButton(String label) {
    this.label = label;
  }

  @Override
  public ElementResult prepare(Env env) {
    return new ElementResult("submit", getDefault(), SubmitButton.KEY, this);
  }

  public HTMLProducer getDefault() {
    return producerInfos -> "<input tabindex=\"" + producerInfos.getTabIndex()
        + "\" type=\"submit\" value=\"" + label + "\"><!-- own renderer -->";
  }

}
