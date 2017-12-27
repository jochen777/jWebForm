package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;

public class NumberType implements Element {

  public final static String KEY = "jwebform.element.NumberInput";

  private final int initialNumber;

  public final OneValueElementProcessor oneValueElement;

  public NumberType(String name, Decoration decoration, int initialValue) {
    this.oneValueElement =
        new OneValueElementProcessor(name, decoration, Integer.toString(initialValue));
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
        new StaticElementInfo(oneValueElement.name, getDefault(), 1, KEY), ElementResult.NOCHILDS,
        parsedNumber);
    return result;
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return (pi) -> pi.getTheme().getRenderer().renderInput("number", pi, oneValueElement.decoration,
        "");
  }


  @Override
  public String toString() {
    return String.format("NumberInput. name=%s", oneValueElement.name);
  }


}
