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


// TODO: No value here!, change method!
public class UploadType extends TextType implements Element {

  public final static String KEY = "jwebform.element.UploadInput";


  public UploadType(String name, OneFieldDecoration decoration, 
      Validator validator) {
    super(name, decoration, "", validator);
  }
  
  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    OneValueElementProcessor oneValueElement = new OneValueElementProcessor();
    return oneValueElement.calculateElementResult(env, name, "", validator,
        new StaticElementInfo(name, getDefault(), 1, KEY), this, t -> true);
  }
  

  @Override
  protected HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      Tag inputTag = renderer.generateInputTag(producerInfos, "file", "input");
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();
      return html;
    };
  }

  @Override
  public String toString() {
    return String.format("UploadInput. name=%s", name);
  }

}
