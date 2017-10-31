package jwebform.element;

import java.util.List;
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
import jwebform.view.TagAttributes;

public class NumberType extends TextType implements Element {

  public final static String KEY = "jwebform.element.NumberInput";

  int number;

  public NumberType(String name, OneFieldDecoration decoration, int initialValue,
      Validator validator) {
    super(name, decoration, Integer.toString(initialValue), validator);
    number = initialValue;
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    String val = Integer.toString(number);
    ElementResult result =  oneValueElement.calculateElementResult(env, name, val , validator,
        new StaticElementInfo(name, getDefault(), 1, KEY), this, t -> true);
    try {
      number = Integer.parseInt(result.getValue());
    } catch (NumberFormatException e) {
      number = 0;   // RFE: maybe a second var to indivate, that number is not settable. (or Integer and NULL?)
    }
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

  public int getNumber() {
    return number;
  }

}
