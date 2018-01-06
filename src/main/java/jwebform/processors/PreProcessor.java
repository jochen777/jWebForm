package jwebform.processors;

import java.util.List;
import jwebform.element.structure.ElementContainer;

// runs before all RunProcessors. Purpose: Check if there are unconsensitencies
@FunctionalInterface
public interface PreProcessor {
  public List<ElementContainer> preProcess(List<ElementContainer> elements); 
}
