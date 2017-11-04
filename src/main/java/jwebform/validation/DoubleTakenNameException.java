package jwebform.validation;

// This exeption will be thrown, if you run a form and assigned elements with identical name
public class DoubleTakenNameException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public DoubleTakenNameException(String name) {
    super(String.format("The name %s was taken more than once for this form. Make sure, that you use eache name of each element only once!", name));
  }
}
