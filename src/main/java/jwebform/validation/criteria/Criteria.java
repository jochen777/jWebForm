package jwebform.validation.criteria;

import jwebform.validation.Criterion;

/**
 * A collection of static methods to create commonly used criteria.
 * 
 * Based on work of armandino (at) gmail.com
 */
public class Criteria {
  private static final Email email = new Email();
  private static final ZipCode zipCode = new ZipCode();
  private static final PostcodeCA postcodeCA = new PostcodeCA();

  public static Criterion required() {
    return Required.getInstance();
  }

  public static Criterion and(Criterion... criteria) {
    return new And(criteria);
  }

  public static Criterion or(Criterion... criteria) {
    return new Or(criteria);
  }

  public static Criterion accept(String... values) {
    return new Accept(values);
  }

  public static Criterion min(int min) {
    return new Min(min);
  }

  public static Criterion max(int max) {
    return new Max(max);
  }

  public static Criterion number() {
    return new Number();
  }

  public static Criterion range(int min, int max) {
    return new Range(min, max);
  }

  public static Criterion length(int min, int max) {
    return new Length(min, max);
  }

  public static Criterion exactLength(int length) {
    return new ExactLength(length);
  }

  public static Criterion minLength(int min) {
    return new MinLength(min);
  }

  public static Criterion maxLength(int max) {
    return new MaxLength(max);
  }

  public static Criterion regex(String pattern) {
    return new Regex(pattern);
  }

  public static Criterion startsWith(String... prefix) {
    return new StartsWith(prefix);
  }

  public static Criterion emailAddress() {
    return email;
  }

  public static Criterion strongPassword(int minLength) {
    return new StrongPassword(minLength);
  }

  public static Criterion zipcode() {
    return zipCode;
  }

  public static Criterion postcodeCA() {
    return postcodeCA;
  }

}
