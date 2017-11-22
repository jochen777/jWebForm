package jwebform.element.structure;

import java.util.List;

import jwebform.element.RadioType.RadioInputEntry;

// renders one element of a radioType
public interface RadioRenderer {

  String renderInputs(ProducerInfos pi, List<RadioInputEntry> entries);
}
