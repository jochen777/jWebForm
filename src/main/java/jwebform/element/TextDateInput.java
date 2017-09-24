package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.PrepareInfos;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Themable;
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
public class TextDateInput implements Themable {
	
	public final static String KEY = "jwebform.element.TextDateInput";

  final private String name;

  final private Validator validator;

  final private LocalDate initialValue;
  final public OneFieldDecoration decoration;

  final private TextInput day;
  final private TextInput month;
  final private TextInput year;

  public TextDateInput(String name, OneFieldDecoration decoration, LocalDate initialValue,
      Validator validator) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;

    Validator numberValidator = new Validator(Criteria.number());

    this.day = new TextInput(name + "_day", new OneFieldDecoration("Day"),
        String.valueOf(initialValue.getDayOfMonth()), numberValidator);
    this.month = new TextInput(name + "_month", new OneFieldDecoration("Month"),
        String.valueOf(initialValue.getMonthValue()), numberValidator);
    this.year = new TextInput(name + "_year", new OneFieldDecoration("Year"),
        String.valueOf(initialValue.getYear()), numberValidator);

  }


  @Override
  public ElementResult prepare(PrepareInfos renderInfos) {
    ElementResult dayResult = day.prepare(renderInfos);
    ElementResult monthResult = month.prepare(renderInfos);
    ElementResult yearResult = year.prepare(renderInfos);

    List<ElementResult> childs = new ArrayList<>();
    childs.add(dayResult);
    childs.add(monthResult);
    childs.add(yearResult);

    LocalDate value = initialValue;
    ValidationResult validationResult = ValidationResult.ok();
    try {
      value = this.setupValue(this.initialValue, dayResult.getValue(), monthResult.getValue(),
          yearResult.getValue());
    } catch (DateTimeException | NumberFormatException e) {
      validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
    }

    ElementResult result = new ElementResult(name, getDefault(), validationResult,
        value.format(DateTimeFormatter.ISO_DATE), 3,KEY,
        childs, this);
    return result;
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
public String getKey() {
	return KEY;
}



@Override
public HTMLProducer getDefault() {
	return pi -> {
		String errorMessage = "";
	      if (pi.getElementResult().getValidationResult() != ValidationResult.undefined() && !pi.getElementResult().getValidationResult().isValid) {
	        errorMessage = "Problem: " + pi.getElementResult().getValidationResult().getMessage() + "<br>";
	      }
	      ElementResult dayResult = pi.getElementResult().getChilds().get(0);
	      ElementResult monthResult = pi.getElementResult().getChilds().get(1);
	      ElementResult yearResult = pi.getElementResult().getChilds().get(2);
	      String html = decoration.getLabel() + "<br/>" + errorMessage
	          + dayResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex(),
	                  pi.getTheme(), dayResult))

	          + monthResult.getHtml(new ProducerInfos(pi.getFormId(), pi.getTabIndex() + 1,
	                  pi.getTheme(), monthResult))
	          + yearResult.getHtml(new ProducerInfos(pi.getFormId(),
	              pi.getTabIndex() + 2, pi.getTheme(), yearResult))
	          + "<br>" + decoration.getHelptext()+"<-- internal renderer -->";
	      return html;
	};
}

}
