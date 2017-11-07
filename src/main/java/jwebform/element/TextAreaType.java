package jwebform.element;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.OneValueElementProcessor;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.Validator;

public class TextAreaType implements Element {

  public final static String KEY = "jwebform.element.TextAreaInput";

  public final OneValueElementProcessor oneValueElement;

  public TextAreaType(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    oneValueElement = new OneValueElementProcessor(name, decoration, initialValue, validator);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    return oneValueElement.calculateElementResult(env, KEY, getDefault(), this, (t) -> true);
  }

  // very simple version!
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      return renderer.generateHtmlWithSomethingBetween(producerInfos, oneValueElement.decoration,
          "select", "select", () -> Escape.html(producerInfos.getElementResult().getValue()));
    };
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", oneValueElement.name);
  }

}
