package jwebform.element;

import com.coverity.security.Escape;

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

public class TextAreaInput extends TextInput implements Element {

  public final static String KEY = "jwebform.element.TextAreaInput";

  public TextAreaInput(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    super(name, decoration, initialValue, validator);
  }


  // very simple version!
  @Override
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      Tag inputTag = renderer.generateInputTag(producerInfos, "text", "textarea");
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml()
          + Escape.html(producerInfos.getElementResult().getValue()) + inputTag.getEndHtml();
      return html;
    };
  }

  @Override
  public String toString() {
    return String.format("TextAreaInput. name=%s", name);
  }

}
