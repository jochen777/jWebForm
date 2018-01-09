package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import jwebform.element.structure.Decoration;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
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

  final private String name;


  final private LocalDate initialValue;
  final public Decoration decoration;

  final private ElementContainer day;
  final private ElementContainer month;
  final private ElementContainer year;

  public TextDateType(String name, Decoration decoration, LocalDate initialValue) {
    this.name = name;
    this.initialValue = initialValue;
    this.decoration = decoration;

    Validator numberValidator = new Validator(Criteria.number());

    this.day = new TextType(name + "_day", new Decoration("Day"),
        String.valueOf(initialValue.getDayOfMonth())).of(numberValidator);
    this.month = new TextType(name + "_month", new Decoration("Month"),
        String.valueOf(initialValue.getMonthValue())).of(numberValidator);
    this.year = new TextType(name + "_year", new Decoration("Year"),
        String.valueOf(initialValue.getYear())).of(numberValidator);

  }


  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    Map<ElementContainer, ElementResult> childs =
        env.getProcessor().processElements(env, day, month, year);

    LocalDate dateValue = initialValue;
    ValidationResult validationResult = ValidationResult.undefined();
    String dateValStr = "";
    if (env.isSubmitted()) {
      try {
        dateValue = this.setupValue(this.initialValue, childs.get(day).getValue(),
            childs.get(month).getValue(), childs.get(year).getValue());
        dateValStr = dateValue.format(DateTimeFormatter.ISO_DATE);
      } catch (DateTimeException | NumberFormatException e) {
        validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
      }
    }

    ElementResult result = new ElementResult(dateValStr,
        new StaticElementInfo(name, getDefault(), 3), childs, dateValue);

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

      Map<ElementContainer, ElementResult> childs = pi.getElementResult().getChilds();
      ElementResult dayResult = childs.get(day);
      ElementResult monthResult = childs.get(month);
      ElementResult yearResult = childs.get(year);
      String html = decoration.getLabel() + "<br/>" + errorMessage
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex(), pi.getTheme(), dayResult, day)
              .getHtml()
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1, pi.getTheme(), monthResult,
              month).getHtml()
          + new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 2, pi.getTheme(), yearResult, year)
              .getHtml()
          + "<br>" + decoration.getHelptext();
      return html;
    };
  }


}
