package jwebform.element;

import java.util.LinkedHashMap;
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
import jwebform.view.TagAttributes;

public class TextAreaType extends TextType implements Element {

  public final static String KEY = "jwebform.element.TextAreaInput";

  public TextAreaType(String name, OneFieldDecoration decoration, String initialValue,
      Validator validator) {
    super(name, decoration, initialValue, validator);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    return oneValueElement.calculateElementResult(env, name, initialValue, validator,
        new StaticElementInfo(name, getDefault(), 1, KEY), this, t -> true);
  }
  
  // very simple version!
  @Override
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
      attrs.put("tabindex", Integer.toString(producerInfos.getTabIndex()));
      attrs.put("name", producerInfos.getNameOfInput());
      TagAttributes inputTagAttr = new TagAttributes(attrs);
      Tag inputTag = new Tag("textarea", inputTagAttr);
      
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
