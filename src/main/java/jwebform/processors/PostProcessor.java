package jwebform.processors;

import java.util.Map;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;

// will be called after the elements were processed
public interface PostProcessor {
  Map<ElementContainer, ElementResult> postProcess(Map<ElementContainer, ElementResult> results);
}
