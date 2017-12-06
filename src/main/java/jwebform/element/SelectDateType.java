package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import jwebform.element.structure.CommonSelects;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;
import jwebform.view.StringUtils;

/**
 * Date-Input with dropdown selects
 * 
 * @author jochen
 *
 */
public class SelectDateType implements Element {

  public final static String KEY = "jwebform.element.SelectDateInput";

  final private String name;


  final private LocalDate initialValue;
  final public OneFieldDecoration decoration;

  final private ElementContainer day;
  final private ElementContainer month;
  final private ElementContainer year;

  public SelectDateType(String name, OneFieldDecoration decoration, LocalDate initialValue,
      int yearStart, int yearEnd) {
    this.name = name;
    this.initialValue = initialValue;
    this.decoration = decoration;

    Validator numberValidator = new Validator(Criteria.number());
    this.day = new SelectType(name + "_day", new OneFieldDecoration("Day"),
        String.valueOf(initialValue.getDayOfMonth()), CommonSelects.build().buildDays())
            .of(numberValidator);
    this.month = new SelectType(name + "_month", new OneFieldDecoration("Month"),
        String.valueOf(initialValue.getMonthValue()), CommonSelects.build().buildMonths())
            .of(numberValidator);;
    this.year = new SelectType(name + "_year", new OneFieldDecoration("Year"),
        String.valueOf(initialValue.getYear()), CommonSelects.build().getYears(yearStart, yearEnd))
            .of(numberValidator);;

  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    Map<ElementContainer, ElementResult> results =
        env.getForm().processElements(env, day, month, year);
    // ElementResult dayResult = day.apply(env);
    // ElementResult monthResult = month.apply(env);
    // ElementResult yearResult = year.apply(env);

    // RFE: Ugly
    if (env.isSubmitted()) {
      Validator numberValidator = new Validator(Criteria.number());
      // dayResult =
      // dayResult.cloneWithNewValidationResult(numberValidator.validate(dayResult.getValue()));
      // monthResult = monthResult
      // .cloneWithNewValidationResult(numberValidator.validate(monthResult.getValue()));
      // yearResult =
      // yearResult.cloneWithNewValidationResult(numberValidator.validate(yearResult.getValue()));
    }

    // List<ElementResult> childs = new ArrayList<>();
    // childs.add(dayResult);
    // childs.add(monthResult);
    // childs.add(yearResult);

    LocalDate dateValue = initialValue;
    ValidationResult validationResult = ValidationResult.undefined();
    String dateValStr = "";
    if (env.isSubmitted()) {
      try {
        dateValue = this.setupValue(this.initialValue, results.get(day).getValue(),
            results.get(month).getValue(), results.get(year).getValue());
        dateValStr = dateValue.format(DateTimeFormatter.ISO_DATE);
        // TODO: validationResult = validator.validate(dateValStr);
      } catch (DateTimeException | NumberFormatException e) {
        validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
      }
    }

    // ElementResult result = new ElementResult(dateValStr,
    // new StaticElementInfo(name, getDefault(), 3, KEY), childs, dateValue);

    ElementResult result = new ElementResult(dateValStr,
        new StaticElementInfo(name, getDefault(), 3, KEY), ElementResult.NOCHILDS, dateValue);

    if (validationResult != ValidationResult.undefined()) {
      return result.cloneWithNewValidationResult(validationResult);
    }
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
      return "";
      // ElementResult dayResult = pi.getElementResult().getChilds().get(0);
      // ElementResult monthResult = pi.getElementResult().getChilds().get(1);
      // ElementResult yearResult = pi.getElementResult().getChilds().get(2);
      // String html = decoration.getLabel() + "<br/>" + errorMessage
      // + new ProducerInfos(pi.getFormId(), pi.getTabIndex(), pi.getTheme(), dayResult,
      // new ElementContainer(day, new Validator())).getHtml()
      // + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1, pi.getTheme(), monthResult,
      // new ElementContainer(month, new Validator())).getHtml()
      // + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 2, pi.getTheme(), yearResult,
      // new ElementContainer(year, new Validator())).getHtml()
      // + "<br>" + decoration.getHelptext();
      // return html;
    };
  }


}
