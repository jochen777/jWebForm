package jwebform.validation;

/**
 * A criterion that checks a formchecker fieldType
 * 
 */
@FunctionalInterface
public interface Criterion {
  /**
   * Tests whether the specified value satisfies this criterion.
   * 
   * @param value to be tested against this criterion.
   * @return a ValidationResult which holds true or false for validaton result and a potential
   *         errormsg
   */
  ValidationResult validate(String value);

}
