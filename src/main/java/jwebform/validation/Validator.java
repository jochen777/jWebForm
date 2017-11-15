package jwebform.validation;

import java.util.ArrayList;
import java.util.List;

import jwebform.validation.criteria.MaxLength;

/**
 * Validator, that checks a form-element
 * 
 * @author jochen
 *
 */
public class Validator {

  private final List<Criterion> criteria = new ArrayList<>();

  public Validator(Criterion... inputCriterium) {
    for (Criterion cirterion : inputCriterium) {
      criteria.add(cirterion);
    }
  }

  public boolean containsExactCriterion(Criterion criterionToSearch) {
    for (Criterion criterion : criteria) {
      if (criterionToSearch == criterion) {
        return true;
      }
    }
    return false;
  }

  public MaxLength getMaxLen() {
    for (Criterion criterion : criteria) {
      if (criterion instanceof MaxLength) { // this
        return (MaxLength) criterion;
      }
    }
    return null;
  }


  public ValidationResult validate(String value) { // RFE: Better just
                                                   // object??
    return allCriteriaSatisfied(value);
  }

  // RFE: Maybe return here an array? So we can have more than one
  // error-message per field.
  private ValidationResult allCriteriaSatisfied(String value) {
    for (Criterion criterion : criteria) {
      ValidationResult vr = criterion.validate(value);
      if (!vr.isValid()) {
        return vr;
      }
    }

    return ValidationResult.ok();
  }

}
