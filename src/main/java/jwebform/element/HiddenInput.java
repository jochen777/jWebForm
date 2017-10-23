package jwebform.element;

import com.coverity.security.Escape;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.env.Env.EnvWithSubmitInfo;

public class HiddenInput implements Element {

  public static String KEY = "jwebform.element.HiddenInput";

  public final String name;
  public final String value;

  public HiddenInput(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    return new ElementResult(name, getDefault(), HiddenInput.KEY, this);
  }

  public HTMLProducer getDefault() {
    return producerInfos -> "<input type=\"hidden\" name=\"" + name + "\" value=\""
        + Escape.html(value) + "\">\n";
  }

}
