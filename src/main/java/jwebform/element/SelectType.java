package jwebform.element;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.env.Env.EnvWithSubmitInfo;

public class SelectType implements SingleType {

  final public List<SelectInputEntry> entries;

  public final OneValueElementProcessor oneValueElement;
  public final List<SelectInputEntryGroup> groups;
  private final static List<SelectInputEntryGroup> EMPTY_GROUPS = new ArrayList<>(); 


  public SelectType(String name, String initialValue, String keys[],
      String values[]) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
    this.entries = generateEntriesFromKeyValues(keys, values);
    this.groups = EMPTY_GROUPS;
  }

  public SelectType(String name, String initialValue, List<SelectInputEntry> entries) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
    this.entries = entries;
    this.groups = EMPTY_GROUPS;
  }
  
  // somewhat fishy because groups and entries seems to be the same type, so we have to change the order of inputs
  public SelectType(String name, List<SelectInputEntryGroup> groups, String initialValue) {
    this.oneValueElement = new OneValueElementProcessor(name, initialValue);
    this.entries = new ArrayList<>();
    this.groups = groups;
  }
  
  
  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResultWithInputCheck(env, getDefault(), 
        t -> ensureValueIsAllowed(t));
  }

  /**
   * This ensures, that only the keys will be allowed for valid values.
   * 
   * @param fetchValue
   * @return
   */
  private boolean ensureValueIsAllowed(String fetchValue) {
    if (!groups.isEmpty()) {
      for (SelectInputEntryGroup group : groups) {
        if (checkEntries(group.entries, fetchValue)) {
          return true;
        }
      }
      return false;
    } else {
      return checkEntries(entries, fetchValue);
    }
  }

  private boolean checkEntries(List<SelectInputEntry> entries2, String fetchValue) {
    for (SelectInputEntry selectInputEntry : entries2) {
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
        buildEntries(pi.getElementResult().getValue(), entries, groups), pi, pi.getDecoration());
  }


  private String buildEntries(String value, List<SelectInputEntry> entries2, List<SelectInputEntryGroup> groups2) {
    StringBuilder inputTag = new StringBuilder();
    
    if (!groups.isEmpty()) {
      groups.forEach(group ->
      {
          inputTag.append("<optgroup label=\""+group.getLabel()+"\">");
          buildEntries(inputTag, group.getEntries(), value);
          inputTag.append("</optgroup>");
      }
      );
    } else {
      buildEntries(inputTag, entries, value);
    }
    return inputTag.toString();
  }

  
  
  private void buildEntries(StringBuilder inputTag, List<SelectInputEntry> entries2, String inputValue) {
    entries2.forEach(entry -> {
      String sel = "";
      if (entry.key.equals(inputValue)) {
        sel = " SELECTED ";
      }
      inputTag
          .append("<option value=\"" + entry.key + "\"" + sel + ">" + entry.value + "</option>\n");
    });
  }



  // class that represents an entry in the selectInput
  public static class SelectInputEntry {
    private final String key;
    private final String value;
    private final boolean selcted;


    public SelectInputEntry(String key, String value) {
      this(key, value, false);
    }

    public SelectInputEntry(String key, String value, boolean selected) {
      this.key = key;
      this.value = value;
      this.selcted = selected;
    }

    public boolean isSelcted() {
      return selcted;
    }

    public String getKey() {
      return key;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.format("Key: %s Value: %s Selected: %s", key, value, selcted);
    }
  }

  public List<SelectInputEntry> getSelectListWithSelected(String selectedKey) {
    List<SelectInputEntry> resultList = new ArrayList<>();
    entries.forEach((entry) -> {
      if (entry.getKey().equals(selectedKey)) {
        resultList.add(new SelectInputEntry(entry.key, entry.value, true));
      } else {
        resultList.add(entry);
      }
    });
    return resultList;
  }

  // class that groups several inputEntries
  // See: https://www.w3schools.com/tags/tag_optgroup.asp
  public static class SelectInputEntryGroup {
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
