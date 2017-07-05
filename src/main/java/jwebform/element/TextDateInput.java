package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;
import jwebform.view.StringUtils;

/**
 * Date-Input with simple text-fields
 * @author jochen
 *
 */
public class TextDateInput implements TabIndexAwareElement{
	
	final private String name;
	// TBD: Does it make sense to introduce a Label class here?
	final private String label;
	
	//final private LocalDate value;

	final private String helptext;
	
	final private Validator validator;
	
	//final private ValidationResult validationResult;
	
	final private LocalDate initialValue;
	
	
	final private TextInput day;
	final private TextInput month;
	final private TextInput year;
	
	
	public TextDateInput(String formId, String name, Request request, String label, LocalDate initialValue, String helptext, Validator validator) {
		this.name = name;
		this.label = label;
		this.helptext = helptext;
		this.validator = validator;
		this.initialValue = initialValue;
		
		Validator numberValidator = new Validator(Criteria.number());

		this.day = new TextInput(formId, name+"_day", request, "Day", String.valueOf(initialValue.getDayOfMonth()),
				"", "", numberValidator);
		this.month = new TextInput(formId, name+"_month", request, "Month", String.valueOf(initialValue.getMonthValue()), "", "", numberValidator);
		this.year = new TextInput(formId, name+"_year", request, "Year", String.valueOf(initialValue.getYear()), "", "", numberValidator);

	}


	


	public LocalDate getDateValue() {
		return null;
	}

	@Override
	public ElementResult getHtml(RenderInfos renderInfos) {
		ElementResult dayResult = day.getHtml(renderInfos);
		RenderInfos monthRenderInfos = renderInfos.cloneWithNewTabIndexIncrease(day.getTabIndexIncrement());
		ElementResult monthResult = month.getHtml(monthRenderInfos);
		RenderInfos yearRenderInfos = monthRenderInfos.cloneWithNewTabIndexIncrease(month.getTabIndexIncrement());
		ElementResult yearResult = year.getHtml(yearRenderInfos);
		
		LocalDate value = initialValue;
		ValidationResult validationResult = ValidationResult.ok();
		try {
			value = this.setupValue(this.initialValue, dayResult.getValue(), monthResult.getValue(), yearResult.getValue());
		} catch (DateTimeException | NumberFormatException e) {
			validationResult = ValidationResult.fail("jformchecker.wrong_date_format");
		}
		
		ValidationResult validationResultToWorkWith = renderInfos.getOverrideValidationResult()==ValidationResult.undefined()?validationResult:renderInfos.getOverrideValidationResult();
		String errorMessage = "";
		if (validationResultToWorkWith != ValidationResult.undefined() && !validationResultToWorkWith.isValid) {
			errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
		}
		ElementResult result = new ElementResult(name, label + "<br/>" 
				+ 
				errorMessage + dayResult.getHtml() +
				monthResult.getHtml() +
				yearResult.getHtml() + "<br>" + helptext,
				validationResult, 
				value.format(DateTimeFormatter.ISO_DATE)
				
				);
		return result;
	}


	@Override
	public int getTabIndexIncrement() {
		return 3;
	}


	// May throw execption!!
	private LocalDate setupValue(LocalDate initialValue, String dayStr, String monthStr, String yearStr){
		if(StringUtils.isEmpty(dayStr) && 
				StringUtils.isEmpty(monthStr) &&
				StringUtils.isEmpty(yearStr) ) {
			return initialValue;	// TODO: maybe this is wrong: if nothing is entered, it can't be the initial value!
		}
		int day = getDefaultValueFromRequest(dayStr);
		int month = getDefaultValueFromRequest(monthStr);
		int year = getDefaultValueFromRequest(yearStr);
		return LocalDate.of(year, month, day);
	}
	

	private int getDefaultValueFromRequest(String input) {
		return Integer.parseInt(input);
	}
	
	
}
