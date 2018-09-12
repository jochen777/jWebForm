package jwebform.themes.sourcecode;

import jwebform.FormResult;
import jwebform.field.structure.HTMLProducer;

import java.util.Map;

public interface Theme {
  Map<String, HTMLProducer> getHtmlProducer();

  ElementRenderer getRenderer();

  String getStart(FormResult result, String method, boolean html5Validation);

  String getEnd();

  RadioRenderer getRadioRenderer(Theme theme);


}
