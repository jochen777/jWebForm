package jwebform.element.structure;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.env.Env;
import jwebform.processors.ElementResults;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.view.StringUtils;

// Suppporting class for date handling types
public class DateTypeHelper {

  final private ElementContainer day;
  final private ElementContainer month;
  final private ElementContainer year;
  final private LocalDate initialValue;
  final private String name;

  public DateTypeHelper(ElementContainer day, ElementContainer month, ElementContainer year,
      LocalDate initialValue, String name) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.initialValue = initialValue;
    this.name = name;
  }

  public LocalDate setupDateValue(LocalDate initialValue, String dayStr, String monthStr,
      String yearStr) {
    if (StringUtils.isEmpty(dayStr) && StringUtils.isEmpty(monthStr)
        && StringUtils.isEmpty(yearStr)) {
      return initialValue; // TODO: maybe this is wrong: if nothing is entered, it can't be the
      // initial value!
    }
    int day = getDefaultValueFromRequest(dayStr);
    int month = getDefaultValueFromRequest(monthStr);
    int year = getDefaultValueFromRequest(yearStr);
    return LocalDate.of(year, month, day);
  }

  private int getDefaultValueFromRequest(String input) {
    return Integer.parseInt(input);
  }


  public List<FormValidator> getValidators(ElementContainer source) {
    return Collections.singletonList((elements) -> {
      Map<ElementContainer, ValidationResult> validationResult = new HashMap<>();

      ElementResult dayResult = elements.get(day);
      ElementResult monthResult = elements.get(month);
      ElementResult yearResult = elements.get(year);
      try {
        setupDateValue(this.initialValue, dayResult.getValue(), monthResult.getValue(),
            yearResult.getValue());
      } catch (DateTimeException | NumberFormatException e) {
        // validationResult.put(day, ValidationResult.fail("jformchecker.wrong_date_format"));
        // validationResult.put(month, ValidationResult.fail("jformchecker.wrong_date_format"));
        // validationResult.put(year, ValidationResult.fail("jformchecker.wrong_date_format"));
      }
      return validationResult;
    });
  }

  public ElementResult processDateVal(Env.EnvWithSubmitInfo env, ElementResults childs,
      String fallbackTypename) {
    LocalDate dateValue = initialValue;
    ValidationResult validationResult = ValidationResult.undefined();
    String dateValStr = "";
    if (env.isSubmitted()) {
      try {
        dateValue = setupDateValue(this.initialValue, childs.get(day).getValue(),
            childs.get(month).getValue(), childs.get(year).getValue());
        dateValStr = dateValue.format(DateTimeFormatter.ISO_DATE);
        validationResult = ValidationResult.ok();
      } catch (DateTimeException | NumberFormatException e) {
        validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
      }
    }

    ElementResult result = ElementResult.builder().withValue(dateValStr).withChilds(childs)
        .withStaticElementInfo(
            new StaticElementInfo(name, t -> "<!-- " + fallbackTypename + " -->", 3))
        .withValueObject(dateValue).build();

    if (validationResult != ValidationResult.undefined()) {
      return result.cloneWithNewValidationResult(validationResult);
    }
    return result;
  }
}
