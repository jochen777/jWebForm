package jwebform.element.renderer.bootstrap;

import java.util.HashMap;
import java.util.Map;

import jwebform.element.CheckBoxType;
import jwebform.element.LabelType;
import jwebform.element.SelectType;
import jwebform.element.SubmitType;
import jwebform.element.TextDateType;
import jwebform.element.TextType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Theme;

public class BootstrapTheme implements Theme {
  public Map<String, HTMLProducer> htmlProducer;

  public BootstrapTheme() {
    htmlProducer = new HashMap<>();
    htmlProducer.put(TextType.KEY, new FastBootstrapTextInputRenderer());
    htmlProducer.put(TextDateType.KEY, new BootstrapTextDateInputRenderer());
    htmlProducer.put(SubmitType.KEY, new BootstrapSubmitButtonRenderer());
    htmlProducer.put(SelectType.KEY, new BootstrapSelectRenderer());
    htmlProducer.put(CheckBoxType.KEY, new BootstrapCheckboxRenderer());
    htmlProducer.put(LabelType.KEY, new BootstrapLabelRenderer());
    /*
     * textArea, (OK) Number, (OK) Passwort, (OK) radio, (OK) fileupload, (OK)
     * 
     */
  }

  public Map<String, HTMLProducer> getHtmlProducer() {
    return htmlProducer;
  }
}
