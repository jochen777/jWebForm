package jwebform.element;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jwebform.element.structure.TabIndexAwareElement;
import jwebform.element.structure.Validateable;
import jwebform.env.Request;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

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
	
	public TextDateInput(String name, Request request, String label, LocalDate initialValue, String helptext, Validator validator) {
		this.name = name;
		this.label = label;
		this.helptext = helptext;
		this.validator = validator;
		
		// RFE: Better use the values from this.day, this.month, this.year and do the calculation with that!!
		boolean allEmpty = checkEmptys(this.name, request);
		ValidationResult tempValidationResult;
		LocalDate tempDate;
		try {
			tempDate = this.setupValue(request, initialValue, allEmpty);
			tempValidationResult = ValidationResult.ok();
		} catch (DateTimeException | NumberFormatException e) {
			tempValidationResult = ValidationResult.fail("jformchecker.wrong_date_format");
			tempDate = initialValue;
		}
		this.value = tempDate;
		this.validationResult = tempValidationResult;
		Validator numberValidator = new Validator(Criteria.number());
		
		String presetDay = initialValue != null?String.valueOf(initialValue.getDayOfMonth()):"";
		String presetMonth = initialValue != null?String.valueOf(initialValue.getMonthValue()):"";
		String presetYear = initialValue != null?String.valueOf(initialValue.getYear()):"";
		
		this.day = new TextInput(name+"_day", request, "Day", presetDay, "", "", numberValidator);
		this.month = new TextInput(name+"_month", request, "Month", presetMonth, "", "", numberValidator);
		this.year = new TextInput(name+"_year", request, "Year", presetYear, "", "", numberValidator);
	}


	private boolean checkEmptys(String name, Request request) {
		return (request.getParameter(name+"_day") == null &&
				request.getParameter(name+"_month") == null && 
				request.getParameter(name+"_year") == null);
	}


	@Override
	public String getValue() {
		return value.format(DateTimeFormatter.ISO_DATE);
	}


	@Override
	public String getHtml(int tabIndex, ValidationResult overrideValidationResult) {
		ValidationResult validationResultToWorkWith = overrideValidationResult==null?validationResult:overrideValidationResult;
		String errorMessage = "";
		if (validationResultToWorkWith != ValidationResult.undefined() && !validationResultToWorkWith.isValid) {
			errorMessage = "Problem: " + validationResultToWorkWith.getMessage() + "<br>";
		}

		return label + "<br/>" + errorMessage + day.getHtml(tabIndex, null) +
				month.getHtml(tabIndex+1, null) +
				year.getHtml(tabIndex+2, null) + "<br>" + helptext;
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
	private LocalDate setupValue(Request request, LocalDate initialValue, boolean allEmpty){
		if (allEmpty) {
			return initialValue;
		}
		int day = getDefaultValueFromRequest(request, "_day");
		int month = getDefaultValueFromRequest(request, "_month");
		int year = getDefaultValueFromRequest(request, "_year");
		return LocalDate.of(year, month, day);
	}


	private int getDefaultValueFromRequest(Request request, String addition) {
		return Integer.parseInt(request.getParameter(name+addition));
	}
	
	
}
