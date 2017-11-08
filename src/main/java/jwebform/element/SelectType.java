package jwebform.element;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

public class SelectType implements Element {

  public final static String KEY = "jwebform.element.SelectInput";

  final public List<SelectInputEntry> entries;

  public final OneValueElementProcessor oneValueElement;


  // RFE: Add groups too!
  public SelectType(String name, OneFieldDecoration decoration, String initialValue,
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
    for (SelectInputEntry selectInputEntry : entries) {
      if (selectInputEntry.getKey().equals(fetchValue)) {
        return true;
      }
    }
    return false;
  }

  private List<SelectInputEntry> generateEntriesFromKeyValues(String[] keys, String[] values) {
    if (keys.length != values.length) {
      throw new IllegalArgumentException("Key / Values with unequal length");
    }
    List<SelectInputEntry> entriesToCreate = new ArrayList<>();
    for (int i = 0; i < keys.length; i++) {
      entriesToCreate.add(this.generateEntry(keys[i], values[i]));
    }
    return entriesToCreate;
  }

  public SelectInputEntry generateEntry(String key, String value) {
    return new SelectInputEntry(key, value);
  }


  @Override
  public String toString() {
    return String.format("SelectInput. name=%s", oneValueElement.name);
  }


  // very simple version!
  public HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInputComplex("select",
        buildEntries(pi.getElementResult().getValue(), entries), pi, oneValueElement.decoration);
  }


  private String buildEntries(String value, List<SelectInputEntry> entries2) {
    StringBuilder inputTag = new StringBuilder();
    entries2.forEach(entry -> {
      String sel = "";
      if (value != null && value.equals(entry.key)) {
        sel = " SELECTED ";
      }
      inputTag
          .append("<option value=\"" + entry.key + "\"" + sel + ">" + entry.value + "</option>\n");
    });
    return inputTag.toString();
  }

  // class that represents an entry in the selectInput
  public class SelectInputEntry {
    private final String key;
    private final String value;

    public SelectInputEntry(String key, String value) {
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

  // class that groups several inputEntries
  // See: https://www.w3schools.com/tags/tag_optgroup.asp
  public class SelectInputEntryGroup {
    private final String label;
    List<SelectInputEntry> entries;

    public SelectInputEntryGroup(String label, List<SelectInputEntry> entries) {
      this.label = label;
      this.entries = entries;
    }

    public SelectInputEntryGroup(String label) {
      this(label, new ArrayList<>());
    }

    public void addSelectInputEntry(SelectInputEntry entry) {
      entries.add(entry);
    }

    public String getLabel() {
      return label;
    }

    public List<SelectInputEntry> getEntries() {
      return entries;
    }

  }

}
