package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
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
  public ElementResult prepare(EnvWithSubmitInfo env) {
    ElementResult result = super.prepare(env);
    number = Integer.parseInt(result.getValue());
    return result;
  }

  // very simple version!
  protected HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
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
