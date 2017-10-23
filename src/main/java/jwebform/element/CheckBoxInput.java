package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.StandardElementRenderer;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.view.Tag;
import jwebform.view.TagAttributes;

public class CheckBoxInput implements Element {

  public final static String KEY = "jwebform.element.CheckboxInput";

  final private String name;

  final private boolean initialValue;

  final private Validator validator;

  final public OneFieldDecoration decoration;

  public boolean checked;

  public CheckBoxInput(String name, OneFieldDecoration decoration, boolean initialValue,
      Validator validator) {
    this.name = name;
    this.validator = validator;
    this.initialValue = initialValue;
    this.decoration = decoration;
  }

  @Override
  public ElementResult prepare(EnvWithSubmitInfo env) {
    // somewhat ugly, but checkboxes ARE ugly
    String requestVal = env.getEnv().getRequest().getParameter(name);
    System.err.println("Req-val: " + requestVal);
    String value = "true";
    if (!env.isSubmitted()) {
      value = "" + initialValue;
      checked = initialValue;
    } else {
      checked = true;
      if (requestVal == null) {
        value = "";
        checked = false;
      }
    }
    ValidationResult vr = ValidationResult.undefined();
    if (env.isSubmitted()) {
      vr = validator.validate(value);
    }
    System.err.println("Value: " + value + ":: bool: " + checked);
    return new ElementResult(vr, value, new StaticElementInfo(name, getDefault(), 1, KEY), this);
  }


  // very simple version!
  public HTMLProducer getDefault() {
    return producerInfos -> {
      StandardElementRenderer renderer = new StandardElementRenderer();
      String errorMessage = renderer.generateErrorMessage(producerInfos);
      TagAttributes tagAttributes = TagAttributes.empty();
      if (checked) {
        tagAttributes.addEmptyAttribute("checked");
      }
      Tag inputTag = renderer.generateInputTag(producerInfos, "checkbox", "input", tagAttributes);
      String html = decoration.getLabel() + errorMessage + inputTag.getStartHtml();

      return html;
    };
  }

  @Override
  public String toString() {
    return String.format("CheckBoxInput. name=%s", name);
  }

  public boolean isBooleanValue() {
    return checked;
  }

}
