package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.Tag;

public class NumberType extends TextType implements Element {

  public final static String KEY = "jwebform.element.NumberInput";

  private final int initialNumber;

  public NumberType(String name, OneFieldDecoration decoration, int initialValue,
      Validator validator) {
    super(name, decoration, Integer.toString(initialValue), validator);
    initialNumber = initialValue;
  }

  @Override
  // TODO: Test this!
  public ElementResult apply(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    String requestVal = env.getEnv().getRequest().getParameter(name);
    String val = env.isSubmitted()?requestVal:Integer.toString(initialNumber);
    int parsedNumber =0;
    String parsedNumberVal = "";
    try {
      parsedNumber = Integer.parseInt(val);
      parsedNumberVal = Integer.toString(parsedNumber);
    } catch (NumberFormatException e) {
      parsedNumber = 0; 
      parsedNumberVal = "";
    }
    ValidationResult vr = oneValueElement.validate(env, validator, requestVal, val);
    ElementResult result = new ElementResult(vr, parsedNumberVal, new StaticElementInfo(name, getDefault(), 1, KEY), 
        ElementResult.NOCHILDS, this, parsedNumber);
    return result;
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      // TODO: Get rid of type="number"
      Tag inputTag = renderer.generateInputTag(producerInfos, "number", "input");
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();
      return html;
    };
  }


  @Override
  public String toString() {
    return String.format("NumberInput. name=%s", name);
  }


}
