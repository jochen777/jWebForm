package jwebform.element;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;

public class CheckBoxType implements Element {

  public final static String KEY = "jwebform.element.CheckboxInput";

  final private String name;

  final private boolean initialValue;


  final public OneFieldDecoration decoration;


  public CheckBoxType(String name, OneFieldDecoration decoration, boolean initialValue) {
    this.name = name;
    this.initialValue = initialValue;
    this.decoration = decoration;
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo env) {
    // somewhat ugly, but checkboxes ARE ugly
    String requestVal = env.getEnv().getRequest().getParameter(name);
    String value = "true";
    boolean checked;
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
    // TODO
    // if (env.isSubmitted()) {
    // vr = validator.validate(value);
    // }
    return new ElementResult(ValidationResult.undefined(), value,
        new StaticElementInfo(name, getDefault(checked), 1, KEY), ElementResult.NOCHILDS, this,
        Boolean.valueOf(checked));
  }


  public HTMLProducer getDefault(boolean checked) {
    return (pi) -> {
      ElementRenderer renderer = pi.getTheme().getRenderer();
      String aria = renderer.renderAriaDescribedBy(pi, decoration);
      String val = renderer.renderValue(pi);
      String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" name=\""
          + pi.getNameOfInput() + "\" value" + val + (checked ? " checked" : "") + aria + ">";
      return renderer.renderInputFree(inputHtml, pi, decoration);
    };
  }

  @Override
  public String toString() {
    return String.format("CheckBoxInput. name=%s", name);
  }

}
