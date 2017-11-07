package jwebform.element;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;
import jwebform.view.Tag;

public class RadioType implements Element {

  public final static String KEY = "jwebform.element.RadioInput";

  final public List<RadioInputEntry> entries;

  public final OneValueElementProcessor oneValueElement;


  // RFE: Add groups too!
  public RadioType(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator, String keys[], String values[]) {
    this.oneValueElement = new OneValueElementProcessor(name, decoration, initialValue, validator);
    entries = generateEntriesFromKeyValues(keys, values);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this,
        t -> ensureValueIsAllowed(t));
  }

  /**
   * This ensures, that only the keys will be allowed for valid values.
   * 
   * @param fetchValue
   * @return
   */
  private boolean ensureValueIsAllowed(String fetchValue) {
    for (RadioInputEntry selectInputEntry : entries) {
      if (selectInputEntry.getKey().equals(fetchValue)) {
        return true;
      }
    }
    return false;
  }

  private List<RadioInputEntry> generateEntriesFromKeyValues(String[] keys, String[] values) {
    if (keys.length != values.length) {
      throw new IllegalArgumentException("Key / Values with unequal length");
    }
    List<RadioInputEntry> entriesToCreate = new ArrayList<>();
    for (int i = 0; i < keys.length; i++) {
      entriesToCreate.add(this.generateEntry(keys[i], values[i]));
    }
    return entriesToCreate;
  }

  public RadioInputEntry generateEntry(String key, String value) {
    return new RadioInputEntry(key, value);
  }


  @Override
  public String toString() {
    return String.format("RadioInput. name=%s", oneValueElement.name);
  }


  // very simple version!
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      // TODO: This is simply wrong!
      Tag inputTag = renderer.generateInputTag(producerInfos, "input", "radio");
      String html = oneValueElement.decoration.getLabel() + errorMessage + inputTag.getStartHtml()
          + buildEntries(producerInfos.getElementResult().getValue(), entries)
          + inputTag.getEndHtml();

      return html;
    };
  }


  private String buildEntries(String value, List<RadioInputEntry> entries2) {
    StringBuilder inputTag = new StringBuilder();
    entries2.forEach(entry -> {
      inputTag.append(getInputTag(entry.key, value));
    });
    return inputTag.toString();
  }

  public String getInputTag(String curValue, String value) {
    return "<input id=\"form-radio-" + oneValueElement.name + "-" + curValue + "\" "
        + " type=\"radio\" name=\"" + oneValueElement.name + "\"  value=\"" + curValue + "\" "
        + getCheckedStatus(curValue, value) + "" + " " + " >\n";
  }

  private String getCheckedStatus(String _name, String value) {
    if (value != null && value.equals(_name)) {
      return "checked";
    } else {
      return "";
    }
  }

  // class that represents an entry in the selectInput
  public class RadioInputEntry {
    private final String key;
    private final String value;

    public RadioInputEntry(String key, String value) {
      this.key = key;
      this.value = value;
    }

    public String getKey() {
      return key;
    }

    public String getValue() {
      return value;
    }
  }


}
