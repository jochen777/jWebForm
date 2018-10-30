package jwebform.themes.sourcecode;

import java.util.List;

import jwebform.field.RadioType;
import jwebform.model.ProducerInfos;

// renders one element of a radioType
public interface RadioRenderer {

  String renderInputs(ProducerInfos pi, List<RadioType.RadioInputEntry> entries, Theme theme);
}
