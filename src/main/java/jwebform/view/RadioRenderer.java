package jwebform.view;

import java.util.List;

import jwebform.element.RadioType.RadioInputEntry;
import jwebform.element.structure.ProducerInfos;

// renders one element of a radioType
public interface RadioRenderer {

  String renderInputs(ProducerInfos pi, List<RadioInputEntry> entries);
}
