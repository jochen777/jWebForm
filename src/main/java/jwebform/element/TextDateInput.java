package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.Validateable;
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
public class TextDateInput implements TabIndexAwareElement, Validateable {
	
	final private String name;
	// TBD: Does it make sense to introduce a Label class here?
	final private String label;
	
	final private LocalDate value;

	final private String helptext;
	
	final private Validator validator;
	
	final private ValidationResult validationResult;
	
	
	final private TextInput day;
	final private TextInput month;
	final private TextInput year;
	
	
	public TextDateInput(String formId, String name, Request request, String label, LocalDate initialValue, String helptext, Validator validator) {
		this.name = name;
		this.label = label;
		this.helptext = helptext;
		this.validator = validator;
		
		Validator numberValidator = new Validator(Criteria.number());

		this.day = new TextInput(formId, name+"_day", request, "Day", String.valueOf(initialValue.getDayOfMonth()),
				"", "", numberValidator);
		this.month = new TextInput(formId, name+"_month", request, "Month", String.valueOf(initialValue.getMonthValue()), "", "", numberValidator);
		this.year = new TextInput(formId, name+"_year", request, "Year", String.valueOf(initialValue.getYear()), "", "", numberValidator);

		ValidationResult tempValidationResult;
		LocalDate tempDate;
		try {
			tempDate = this.setupValue(initialValue);
			tempValidationResult = ValidationResult.ok();
		} catch (DateTimeException | NumberFormatException e) {
			tempValidationResult = ValidationResult.fail("jformchecker.wrong_date_format");
			tempDate = initialValue;
		}
		this.value = tempDate;
		this.validationResult = tempValidationResult;
		
		
	}


	private boolean checkAllEmpty() {
		return (StringUtils.isEmpty(this.day.getValue())  &&
				StringUtils.isEmpty(this.month.getValue()) && 
				StringUtils.isEmpty(this.year.getValue()));
	}


	@Override
	public String getValue() {
		return value.format(DateTimeFormatter.ISO_DATE);
	}

	public LocalDate getDateValue() {
		return value;
	}

	@Override
	public ElementResult getHtml(RenderInfos renderInfos) {
		ValidationResult validationResultToWorkWith = renderInfos.getOverrideValidationResult()==ValidationResult.undefined()?validationResult:renderInfos.getOverrideValidationResult();
		String errorMessage = "";
		if (validationResultToWorkWith != ValidationResult.undefined() && !validationResultToWorkWith.isValid) {
			errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
		}
		
		ElementResult result = new ElementResult(name, label + "<br/>" 
//				+ 
//				errorMessage + day.getHtml(tabIndex, null) +
//				month.getHtml(tabIndex+1, null) +
//				year.getHtml(tabIndex+2, null) + "<br>" + helptext
				
				);
		return result;
	}

	@Override
	public ValidationResult getValidationResult() {
		return validationResult;
		
	}


	@Override
	public int getTabIndexIncrement() {
		return 3;
	}


	// May throw execption!!
	private LocalDate setupValue(LocalDate initialValue){
		if (checkAllEmpty()) {
			return initialValue;
		}
		int day = getDefaultValueFromRequest(this.day);
		int month = getDefaultValueFromRequest(this.month);
		int year = getDefaultValueFromRequest(this.year);
		return LocalDate.of(year, month, day);
	}


	private int getDefaultValueFromRequest(TextInput input) {
		return Integer.parseInt(input.getValue());
	}
	
	
}
