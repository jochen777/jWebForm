package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.validation.Validator;
import jwebform.view.Tag;

public class UploadInput extends TextInput implements Element {

  public final static String KEY = "jwebform.element.UploadInput";
  

  public UploadInput(String name, OneFieldDecoration decoration, /* you don't want an initial value! */
      Validator validator) {
	  super(name, decoration, "", validator);
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
