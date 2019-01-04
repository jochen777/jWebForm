package jwebform.themes.sourcecode;

import jwebform.field.RadioType;
import jwebform.model.ProducerInfos;

import java.util.List;

// renders one element of a radioType
public interface RadioRenderer {

  String renderInputs(ProducerInfos pi, List<RadioType.RadioInputEntry> entries, Theme theme);
}
