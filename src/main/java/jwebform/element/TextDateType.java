package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.GroupType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;
import jwebform.view.StringUtils;

/**
 * Date-Input with simple text-fields
 * 
 * @author jochen
 *
 */
public class TextDateType implements GroupType {

  final private String name;


  final private LocalDate initialValue;

  final private ElementContainer day;
  final private ElementContainer month;
  final private ElementContainer year;

  public TextDateType(String name, LocalDate initialValue) {
    this.name = name;
    this.initialValue = initialValue;

    Validator numberValidator = new Validator(Criteria.number());

    this.day = new TextType(name + "_day", String.valueOf(initialValue.getDayOfMonth()))
        .of(numberValidator, new Decoration("Day"));
    this.month = new TextType(name + "_month", String.valueOf(initialValue.getMonthValue()))
        .of(numberValidator, new Decoration("Month"));
    this.year = new TextType(name + "_year", String.valueOf(initialValue.getYear()))
        .of(numberValidator, new Decoration("Year"));

  }



  // May throw execption!!
  private LocalDate setupValue(LocalDate initialValue, String dayStr, String monthStr,
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



  @Override
  public List<ElementContainer> getChilds() {
    return Arrays.asList(day, month, year);
  }


  @Override
  public List<FormValidator> getValidators(ElementContainer source) {
    return Arrays.asList((elements) -> {
      Map<ElementContainer, ValidationResult> validationResult = new HashMap<>();

      ElementResult dayResult = elements.get(day);
      ElementResult monthResult = elements.get(month);
      ElementResult yearResult = elements.get(year);
      try {
        this.setupValue(this.initialValue, dayResult.getValue(), monthResult.getValue(),
            yearResult.getValue());
      } catch (DateTimeException | NumberFormatException e) {
        // validationResult.put(day, ValidationResult.fail("jformchecker.wrong_date_format"));
        // validationResult.put(month, ValidationResult.fail("jformchecker.wrong_date_format"));
        // validationResult.put(year, ValidationResult.fail("jformchecker.wrong_date_format"));
      }
      return validationResult;
    });



  }


  @Override
  public ElementResult process(EnvWithSubmitInfo env, Map<ElementContainer, ElementResult> childs) {
    LocalDate dateValue = initialValue;
    ValidationResult validationResult = ValidationResult.undefined();
    String dateValStr = "";
    if (env.isSubmitted()) {
      try {
        dateValue = this.setupValue(this.initialValue, childs.get(day).getValue(),
            childs.get(month).getValue(), childs.get(year).getValue());
        dateValStr = dateValue.format(DateTimeFormatter.ISO_DATE);
        validationResult = ValidationResult.ok();
      } catch (DateTimeException | NumberFormatException e) {
        validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
      }
    }

    ElementResult result = new ElementResult(dateValStr,
        new StaticElementInfo(name, t -> "<!-- text Date -->", 3), childs, dateValue);

    if (validationResult != ValidationResult.undefined()) {
      return result.cloneWithNewValidationResult(validationResult);
    }
    return result;
  }


}
