package jwebform.field;

import java.util.ArrayList;
import java.util.List;
import jwebform.field.helper.OneValueTypeProcessor;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.SingleFieldType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class RadioType implements SingleFieldType {

  final public List<RadioInputEntry> entries;

  public final OneValueTypeProcessor oneValueElement;

  // RFE: Add groups too, allow nothing as initial value
  public RadioType(String name, String initialValue, String keys[], String values[]) {
    this.oneValueElement = new OneValueTypeProcessor(name, initialValue);
    entries = generateEntriesFromKeyValues(keys, values);
  }

  @Override
  public FieldResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateTypeWithInputCheck(env, t -> "<!-- radio -->",
        this::ensureValueIsAllowed);
  }

  /**
   * This ensures, that only the keys will be allowed for valid values.
   * 
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


  // class that represents an entry in the selectInput
  public class RadioInputEntry {
    protected final String key;
    protected final String value;

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

  public class RadioInputEntryWithSelectedInfo extends RadioInputEntry {
    private final boolean selected;

    public RadioInputEntryWithSelectedInfo(String key, String value, boolean selected) {
      super(key, value);
      this.selected = selected;
    }

    @Override
    public String toString() {
      return String.format("Key: %s Value: %s, Selected: %s", key, value, selected);
    }

    public boolean isSelected() {
      return selected;
    }



  }


  public List<RadioInputEntry> getEntries() {
    return entries;
  }

  public List<RadioInputEntryWithSelectedInfo> getEntryListWithSelected(String selectedKey) {
    List<RadioInputEntryWithSelectedInfo> resultList = new ArrayList<>();
    entries.forEach((entry) -> {
      boolean selected = false;
      if (entry.getKey().equals(selectedKey)) {
        selected = true;
      }
      resultList.add(new RadioInputEntryWithSelectedInfo(entry.key, entry.value, selected));
    });
    return resultList;
  }


}
