package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.SingleType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;

public class NumberType implements SingleType {

  private final int initialNumber;

  public final OneValueElementProcessor oneValueElement;

  public NumberType(String name, int initialValue) {
    this.oneValueElement =
        new OneValueElementProcessor(name, Integer.toString(initialValue));
    initialNumber = initialValue;
  }

  @Override
  // TODO: Test this!
  public ElementResult apply(EnvWithSubmitInfo env) {
    String requestVal = env.getEnv().getRequest().getParameter(oneValueElement.name);
    String val = env.isSubmitted() ? requestVal : Integer.toString(initialNumber);
    int parsedNumber = 0;
    String parsedNumberVal = "";
    try {
      parsedNumber = Integer.parseInt(val);
      parsedNumberVal = Integer.toString(parsedNumber);
    } catch (NumberFormatException e) {
      parsedNumber = 0;
      // maybe better set a validaion problem here!
      parsedNumberVal = "";
    }
    // ValidationResult vr = oneValueElement.validate(env, oneValueElement.validator, requestVal,
    // val);
    ElementResult result = new ElementResult(parsedNumberVal,
        new StaticElementInfo(oneValueElement.name, getDefault(), 1), ElementResult.NOCHILDS,
        parsedNumber);
    return result;
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("number", pi, pi.getDecoration(),
        "");
  }


  @Override
  public String toString() {
    return String.format("NumberInput. name=%s", oneValueElement.name);
  }


}
