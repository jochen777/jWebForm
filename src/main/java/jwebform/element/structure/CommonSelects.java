package jwebform.element.structure;

import jwebform.element.SelectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Common selects. Like gender, months...
 * @author jochen
 *
 */
public class CommonSelects {
	
    private CommonSelects() {}
  
	public static CommonSelects build() {
		return new CommonSelects();
	}

	private static final String[] months  = {"january", "february", "march", "april", "may", "june", "july"
			, "august", "september", "october", "november", "december"};

	
	public List<SelectType.SelectInputEntry> buildDays() { 
		return buildMap(1, 31, "jformchecker.select.day");
	}

	public  List<SelectType.SelectInputEntry> buildMonths() { 
	  List<SelectType.SelectInputEntry> monthsList = new ArrayList<>();
		for (int i=1; i<=12; i++) {
		  monthsList.add(new SelectType.SelectInputEntry(Integer.toString(i), "jformchecker.select."+ months[i-1]));
		}
		return monthsList;
	}

	public List<SelectType.SelectInputEntry>  getYears(int startYear, int endYear) { 
    if (startYear < endYear) {
      int tmp = startYear;
      startYear = endYear;
      endYear = tmp;
    }
		return builReverseMap(startYear, endYear, "jformchecker.select.year");
	}

	
	public List<SelectType.SelectInputEntry> getGenderSelect() { 
      List<SelectType.SelectInputEntry> genderSelect = new ArrayList<>();
      addToList(genderSelect, "f", "jformchecker.select.female");
      addToList(genderSelect, "m", "jformchecker.select.male");
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
