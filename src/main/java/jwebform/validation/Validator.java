package jwebform.validation;

import jwebform.validation.criteria.Criteria;
import jwebform.validation.criteria.MaxLength;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Validator, that checks a field
 * 
 * @author jochen
 *
 */
public class Validator {

  private final Criterion [] criteria;
  private static final Validator EMPTY_VALIDATOR = new Validator();

  public Validator(Criterion... inputCriterium) {
    criteria = inputCriterium;
  }

  public boolean isRequired() {
    for (Criterion criterion : criteria) {
      if (criterion == Criteria.required()) { // this is ok, because it uses a sigelton
        return true;
      }
    }
    return false;
  }


  public Optional<MaxLength> getMaxLen() {
    for (Criterion criterion : criteria) {
      if (criterion instanceof MaxLength) { // this
        return Optional.of((MaxLength) criterion);
      }
    }
    return Optional.empty();
  }


  public ValidationResult validate(String value) {
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
