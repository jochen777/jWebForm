package jwebform.processors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;


public class CheckDoubleElementsPostProcessor implements PostProcessor{

  @Override
  public Map<ElementContainer, ElementResult> postProcess(
      Map<ElementContainer, ElementResult> results) {
    Set<String> availElements = new HashSet<>();
    results.forEach((k, v) -> {
      // empty names are skipped
      if (v.getStaticElementInfo().getName() != ElementResult.NO_NAME
          && !availElements.add(v.getStaticElementInfo().getName())) {
        throw new DoubleTakenNameException(v.getStaticElementInfo().getName());
      }
    });
    return results;
  }

  

}
