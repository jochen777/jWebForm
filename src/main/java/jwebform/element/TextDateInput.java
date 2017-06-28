package jwebform.element;

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
		this.value = this.setupValue(request, initialValue);
		this.validationResult = this.validate(request);
		Validator numberValidator = new Validator(Criteria.number());
		
		String presetDay = initialValue != null?String.valueOf(initialValue.getDayOfMonth()):"";
		String presetMonth = initialValue != null?String.valueOf(initialValue.getMonthValue()):"";
		String presetYear = initialValue != null?String.valueOf(initialValue.getYear()):"";
		
		this.day = new TextInput(name+"day", request, "Day", presetDay, "", "", numberValidator);
		this.month = new TextInput(name+"month", request, "Month", presetMonth, "", "", numberValidator);
		this.year = new TextInput(name+"year", request, "Year", presetYear, "", "", numberValidator);
	}


	@Override
	public String getValue() {
		return value.format(DateTimeFormatter.ISO_DATE);
	}


	@Override
	public String getHtml(int tabIndex, ValidationResult overrideValidationResult) {
		ValidationResult validationResultToWorkWith = validationResult;
		if (overrideValidationResult != null) {
			validationResultToWorkWith = overrideValidationResult;
		}
		return label + "<br/>" + day.getHtml(tabIndex, null) +
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


	private LocalDate setupValue(Request request, LocalDate initialValue){
		String day;
		if (request.getParameter(name+"_DAY") != null) {
			day = request.getParameter(name+"_DAY");
		}
		return initialValue;
	}
	
	private ValidationResult validate(Request request) {
		if (request.getParameter(name) != null) {
			return validator.validate(this);
		}
		return ValidationResult.undefined();
	}


	
	
	
}
