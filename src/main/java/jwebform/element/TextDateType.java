package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
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
public class TextDateType implements Element {

  public final static String KEY = "jwebform.element.TextDateInput";

  final private String name;


  final private LocalDate initialValue;
  final public OneFieldDecoration decoration;

  final private TextType day;
  final private TextType month;
  final private TextType year;

  public TextDateType(String name, OneFieldDecoration decoration, LocalDate initialValue) {
    this.name = name;
    this.initialValue = initialValue;
    this.decoration = decoration;


    this.day = new TextType(name + "_day", new OneFieldDecoration("Day"),
        String.valueOf(initialValue.getDayOfMonth())); // TODO: , numberValidator
    this.month = new TextType(name + "_month", new OneFieldDecoration("Month"),
        String.valueOf(initialValue.getMonthValue()));
    this.year = new TextType(name + "_year", new OneFieldDecoration("Year"),
        String.valueOf(initialValue.getYear()));

  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    ElementResult dayResult = day.apply(env);
    ElementResult monthResult = month.apply(env);
    ElementResult yearResult = year.apply(env);

    if (env.isSubmitted()) {
      Validator numberValidator = new Validator(Criteria.number());
      dayResult =
          dayResult.cloneWithNewValidationResult(numberValidator.validate(dayResult.getValue()));
      monthResult = monthResult
          .cloneWithNewValidationResult(numberValidator.validate(monthResult.getValue()));
      yearResult =
          yearResult.cloneWithNewValidationResult(numberValidator.validate(yearResult.getValue()));
    }

    List<ElementResult> childs = new ArrayList<>();
    childs.add(dayResult);
    childs.add(monthResult);
    childs.add(yearResult);

    LocalDate dateValue = initialValue;
    ValidationResult validationResult = ValidationResult.ok();
    String dateValStr = "";
    try {
      dateValue = this.setupValue(this.initialValue, dayResult.getValue(), monthResult.getValue(),
          yearResult.getValue());
      dateValStr = dateValue.format(DateTimeFormatter.ISO_DATE);
      // TODO: validationResult = validator.validate(dateValStr);
    } catch (DateTimeException | NumberFormatException e) {
      validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
    }

    ElementResult result = new ElementResult(ValidationResult.undefined() /** TODO */
        , dateValStr, new StaticElementInfo(name, getDefault(), 3, KEY), childs, this, dateValue);

    return result;
  }



  // May throw execption!!
  private LocalDate setupValue(
      LocalDate initialValue,
      String dayStr,
      String monthStr,
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


  public HTMLProducer getDefault() {
    return (pi) -> {
      String errorMessage = "";

      ValidationResult vr = pi.getElementResult().getValidationResult();

      if (vr != ValidationResult.undefined() && !vr.isValid) {
        errorMessage = "Problem: " + vr.getMessage() + "<br>";
      }
      ElementResult dayResult = pi.getElementResult().getChilds().get(0);
      ElementResult monthResult = pi.getElementResult().getChilds().get(1);
      ElementResult yearResult = pi.getElementResult().getChilds().get(2);
      String html = decoration.getLabel() + "<br/>" + errorMessage
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex(), pi.getTheme(), dayResult).getHtml()
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1, pi.getTheme(), monthResult)
              .getHtml()
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 2, pi.getTheme(), yearResult)
              .getHtml()
          + "<br>" + decoration.getHelptext();
      return html;
    };
  }


}
