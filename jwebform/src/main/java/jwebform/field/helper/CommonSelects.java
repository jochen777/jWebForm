package jwebform.field.helper;

import java.util.ArrayList;
import java.util.List;
import jwebform.field.SelectType;

/**
 * Common convenience setups for select fields.
 * <p>
 * Build-in: * Months (january ... december) * Days (1..31) * Years (start ... end) * Gender
 * <p>
 * RFE: Add Days (monday...sunday),
 */
public class CommonSelects {

  private CommonSelects() {}

  public static CommonSelects build() {
    return new CommonSelects();
  }

  private static final String[] months = {"january", "february", "march", "april", "may", "june",
      "july", "august", "september", "october", "november", "december"};


  public List<SelectType.SelectInputEntry> buildDays() {
    List<SelectType.SelectInputEntry> daySelect = buildMap(1, 31, "jwebform.select.day");
    return daySelect;
  }

  public List<SelectType.SelectInputEntry> buildMonths() {
    List<SelectType.SelectInputEntry> monthsList = new ArrayList<>();
    monthsList.add(new SelectType.SelectInputEntry("", "jwebform.select.month"));
    for (int i = 1; i <= 12; i++) {
      monthsList.add(
          new SelectType.SelectInputEntry(Integer.toString(i), "jwebform.select." + months[i - 1]));
    }
    return monthsList;
  }

  /**
   * will always return a list of years from startYear to endYear. Example:
   * 
   * startyear: 2010, endyear: 2012: => 2010,2011,2012 startyear: 2012, endyear: 2010: =>
   * 2012,2011,2010
   * 
   * @param startYear
   * @param endYear
   * @return
   */
  public List<SelectType.SelectInputEntry> getYears(int startYear, int endYear) {
    if (startYear < endYear) {
      return buildMap(startYear, endYear, "jwebform.select.year");
    } else {
      return builReverseMap(startYear, endYear, "jwebform.select.year");
    }
  }


  public List<SelectType.SelectInputEntry> getGenderSelect() {
    List<SelectType.SelectInputEntry> genderSelect = new ArrayList<>();
    addToList(genderSelect, "f", "jwebform.select.female");
    addToList(genderSelect, "m", "jwebform.select.male");
    return genderSelect;
  }


  private List<SelectType.SelectInputEntry> buildMap(int start, int end, String first) {
    List<SelectType.SelectInputEntry> dayMap = new ArrayList<>();
    addToList(dayMap, "", first);
    for (int i = start; i <= end; i++) {
      addToList(dayMap, Integer.toString(i), Integer.toString(i));
    }
    return dayMap;
  }

  private List<SelectType.SelectInputEntry> builReverseMap(int start, int end, String first) {
    List<SelectType.SelectInputEntry> entries = new ArrayList<>();
    entries.add(new SelectType.SelectInputEntry("", first));
    for (int i = start; i >= end; i--) {
      entries.add(new SelectType.SelectInputEntry(Integer.toString(i), Integer.toString(i)));
    }
    return entries;
  }

  private void addToList(List<SelectType.SelectInputEntry> list, String key, String value) {
    list.add(new SelectType.SelectInputEntry(key, value));
  }

}
