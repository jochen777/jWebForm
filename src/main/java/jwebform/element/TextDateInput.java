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
	
	//final private LocalDate value;

	
	final private Validator validator;
	
	//final private ValidationResult validationResult;
	
	final private LocalDate initialValue;
	final private OneFieldDecoration decoration;
	
	final private TextInput day;
	final private TextInput month;
	final private TextInput year;
	
	public TextDateInput(String name, OneFieldDecoration decoration, LocalDate initialValue, Validator validator) {
		this.name = name;
		this.validator = validator;
		this.initialValue = initialValue;
		this.decoration = decoration;
		
		Validator numberValidator = new Validator(Criteria.number());

		this.day = new TextInput(name+"_day", new OneFieldDecoration("Day"), String.valueOf(initialValue.getDayOfMonth()),
				 numberValidator);
		this.month = new TextInput(name+"_month",  new OneFieldDecoration("Month"), String.valueOf(initialValue.getMonthValue()),  numberValidator);
		this.year = new TextInput(name+"_year",  new OneFieldDecoration("Year"), String.valueOf(initialValue.getYear()), numberValidator);

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
		ElementResult result = new ElementResult(name, decoration.getLabel() + "<br/>" 
				+ 
				errorMessage + dayResult.getHtml() +
				monthResult.getHtml() +
				yearResult.getHtml() + "<br>" + decoration.getHelptext(),
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
