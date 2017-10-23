package jwebform.element;

import java.util.ArrayList;
import java.util.List;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;
import jwebform.view.Tag;

public class SelectInput implements Element {

  public final static String KEY = "jwebform.element.SelectInput";

  final private String name;
  final private String initialValue;
  final private Validator validator;
  final private List<SelectInputEntry> entries;
  final private OneFieldDecoration decoration;


  // RFE: Add groups too!
  public SelectInput(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator, String keys[], String values[]) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;
    entries = generateEntriesFromKeyValues(keys, values);
  }

  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    return oneValueElement.calculateElementResult(env, name, initialValue, validator,
        new StaticElementInfo(name, getDefault(), 1, KEY), this, t -> ensureValueIsAllowed(t));
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
    return String.format("SelectInput. name=%s", name);
  }


  // very simple version!
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      Tag inputTag = renderer.generateInputTag(producerInfos, "select", "select");
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml()
          + buildEntries(producerInfos.getElementResult().getValue(), entries)
          + inputTag.getEndHtml();

      return html;
    };
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
