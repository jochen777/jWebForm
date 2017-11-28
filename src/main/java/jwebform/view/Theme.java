package jwebform.view;

import java.util.List;
import java.util.Map;

import jwebform.element.structure.Behaviour;
import jwebform.element.structure.HTMLProducer;

public interface Theme {
  Map<String, HTMLProducer> getHtmlProducer();

  ElementRenderer getRenderer();

  List<Behaviour> getGlobalBehaviours();

  String getStart(String formId, String method, boolean html5Validation, boolean uplaodform);

  String getEnd();

  RadioRenderer getRadioRenderer();


}
