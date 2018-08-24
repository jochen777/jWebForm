package jwebform.field;

import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.env.Env;
import jwebform.processor.FieldResults;
import jwebform.processor.ElementValdationResults;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

// Suppporting class for date handling types
class DateTypeHelper {

  final private Field day;
  final private Field month;
  final private Field year;
  final private LocalDate initialValue;
  final private String name;

  public DateTypeHelper(
    Field day, Field month, Field year,
      LocalDate initialValue, String name) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.initialValue = initialValue;
    this.name = name;
  }

  private LocalDate setupDateValue(
    LocalDate initialValue, String dayStr, String monthStr, String yearStr) {
    if (isEmpty(dayStr) && isEmpty(monthStr)
        && isEmpty(yearStr)) {
      return initialValue; // TODO: maybe this is wrong: if nothing is entered, it can't be the
      // initial value!
    }
    int dayInt = getDefaultValueFromRequest(dayStr);
    int monthInt = getDefaultValueFromRequest(monthStr);
    int yearInt = getDefaultValueFromRequest(yearStr);
    return LocalDate.of(yearInt, monthInt, dayInt);
  }

  private int getDefaultValueFromRequest(String input) {
    return Integer.parseInt(input);
  }


  public List<FormValidator> getValidators() {
    return Collections.singletonList((elements) -> {
      ElementValdationResults validationResult = new ElementValdationResults();

      FieldResult dayResult = elements.get(day);
      FieldResult monthResult = elements.get(month);
      FieldResult yearResult = elements.get(year);
      try {
        setupDateValue(this.initialValue, dayResult.getValue(), monthResult.getValue(),
            yearResult.getValue());
      } catch (DateTimeException | NumberFormatException e) {
        // nothing have to happen here, because the validation for the group will be set to false
      }
      return validationResult;
    });
  }

  public FieldResult processDateVal(Env.EnvWithSubmitInfo env, FieldResults childs,
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

    FieldResult result = FieldResult.builder().withValue(dateValStr).withChilds(childs)
        .withStaticElementInfo(
            new StaticFieldInfo(name, t -> "<!-- " + fallbackTypename + " -->", 3))
        .withValueObject(dateValue).build();

    if (validationResult != ValidationResult.undefined()) {
      return result.cloneWithNewValidationResult(validationResult);
    }
    return result;
  }

  private boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

}
