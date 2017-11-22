package jwebform.view;

import java.util.List;
import java.util.Map;

import jwebform.element.structure.Behaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RadioRenderer;

public interface Theme {
  Map<String, HTMLProducer> getHtmlProducer();

  ElementRenderer getRenderer();

  List<Behaviour> getGlobalBehaviours();

  String getStart(StartEndRenderer startEndRenderer);

  String getEnd(StartEndRenderer startEndRenderer);

  RadioRenderer getRadioRenderer();
}
