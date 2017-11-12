package jwebform.element.structure;

import java.util.List;
import java.util.Map;

public interface Theme {
  Map<String, HTMLProducer> getHtmlProducer();

  ElementRenderer getRenderer();
  
  List<Behaviour> getGlobalBehaviours();
}
