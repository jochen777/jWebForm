package jwebform.element.renderer.bootstrap;

import java.util.HashMap;
import java.util.Map;
import jwebform.element.SubmitButton;
import jwebform.element.TextDateInput;
import jwebform.element.TextInput;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;

  public BootstrapTheme() {
    htmlProducer = new HashMap<>();
    htmlProducer.put(TextInput.KEY, new FastBootstrapTextInputRenderer());
    htmlProducer.put(TextDateInput.KEY, new BootstrapTextDateInputRenderer());
    htmlProducer.put(SubmitButton.KEY, new BootstrapSubmitButtonRenderer());
  }

  public Map<String, HTMLProducer> getHtmlProducer() {
    return htmlProducer;
  }
}
