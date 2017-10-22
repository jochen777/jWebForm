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

public class TextInput implements Element {

  public final static String KEY = "jwebform.element.TextInput";
  final private String name;
  final private String initialValue;
  final private Validator validator;
  final public OneFieldDecoration decoration;

  public TextInput(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;
  }

  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    return oneValueElement.calculateElementResult(env, name, initialValue, validator,
        new StaticElementInfo(name, getDefault(), 1, KEY), this, t -> true);
  }

  // very simple version!
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      Tag inputTag = renderer.generateInputTag(producerInfos, "text", "input");
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();
      return html;
    };
  }

  @Override
  public String toString() {
    return String.format("TextInput. name=%s", name);
  }

}
