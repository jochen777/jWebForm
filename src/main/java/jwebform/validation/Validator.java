package jwebform.validation;

import java.util.ArrayList;
import java.util.List;

import jwebform.element.structure.Validateable;

/**
 * Validator, that checks a form-element
 * 
 * @author jochen
 *
 */
public class Validator  {
	
	List <Criterion> criteria = new ArrayList<>();
	
	public Validator(Criterion ... inputCriterium){
		for (Criterion cirterion : inputCriterium) {
			criteria.add(cirterion);
		}
	}

	public ValidationResult validate(Validateable elem) {
		String value = elem.getValue();
		ValidationResult vr = ValidationResult.ok();
		if (value != null) {
			vr = allCriteriaSatisfied(elem);
		} 
		return vr;
	}

	// RFE: Maybe return here an array? So we can have more than one error-message per field.
	private ValidationResult allCriteriaSatisfied(Validateable elem) {
		for (Criterion criterion : criteria) {
			ValidationResult vr = criterion.validate(elem);
			if (!vr.isValid()) {
				return vr;
			}
		}

		return ValidationResult.ok();
	}


}
