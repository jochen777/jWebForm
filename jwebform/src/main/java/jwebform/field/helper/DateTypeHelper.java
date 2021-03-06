package jwebform.field.helper;

import jwebform.env.Env;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.processor.FieldResults;
import jwebform.processor.FieldValdationResults;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Suppporting class for date handling types
public class DateTypeHelper {

  private final Field day;
  private final Field month;
  private final Field year;
  private final String name;

  public DateTypeHelper(Field day, Field month, Field year, String name) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.name = name;
  }

  private Optional<LocalDate> setupDateValue( String dayStr, String monthStr,
      String yearStr) {
    if (isEmpty(dayStr) && isEmpty(monthStr) && isEmpty(yearStr)) {
      return Optional.empty();
    }
    int dayInt = getDefaultValueFromRequest(dayStr);
    int monthInt = getDefaultValueFromRequest(monthStr);
    int yearInt = getDefaultValueFromRequest(yearStr);
    return Optional.of(LocalDate.of(yearInt, monthInt, dayInt));
  }

  private int getDefaultValueFromRequest(String input) {
    return Integer.parseInt(input);
  }


  public List<FormValidator> getValidators() {
    return Collections.singletonList(fieldResults -> {
      FieldValdationResults validationResult = new FieldValdationResults();

      FieldResult dayResult = fieldResults.get(day);
      FieldResult monthResult = fieldResults.get(month);
      FieldResult yearResult = fieldResults.get(year);
      try {
        setupDateValue(dayResult.getValue(), monthResult.getValue(),
            yearResult.getValue());
      } catch (DateTimeException | NumberFormatException e) {
        // nothing have to happen here, because the validation for the group will be set to false
      }
      return validationResult;
    });
  }

  public FieldResult processDateVal(Env.EnvWithSubmitInfo env, FieldResults childs,
      String fallbackTypename) {
    ValidationResult validationResult = ValidationResult.undefined();
    String dateValStr = "";
    Optional<LocalDate> dateToSet = Optional.empty();
    if (env.isSubmitted()) {
      try {
        Optional<LocalDate> dateOptinal = setupDateValue(
            childs.get(day).getValue(), childs.get(month).getValue(), childs.get(year).getValue());
        dateToSet = dateOptinal;
        if (dateOptinal.isPresent()) {
          dateValStr = dateOptinal.get().format(DateTimeFormatter.ISO_DATE);
        } else {
          dateValStr = "";
        }
        validationResult = ValidationResult.ok();
      } catch (DateTimeException | NumberFormatException e) {
        validationResult = ValidationResult.fail("jwebform.wrong_date_format");
      }
    }

    FieldResult result = FieldResult.builder().withValue(dateValStr).withChilds(childs)
        .withStaticFieldInfo(new StaticFieldInfo(name, t -> "<!-- " + fallbackTypename + " -->", 3))
        .withValueObject(dateToSet).build();

    if (validationResult != ValidationResult.undefined()) {
      return result.cloneWithNewValidationResult(validationResult);
    }
    return result;
  }

  private boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

}
