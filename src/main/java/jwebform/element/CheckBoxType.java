package jwebform.element;

import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.SingleType;
import jwebform.element.structure.StaticElementInfo;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.view.ElementRenderer;

public class CheckBoxType implements SingleType {

  final private String name;

  final private boolean initialValue;


  public CheckBoxType(String name, boolean initialValue) {
    this.name = name;
    this.initialValue = initialValue;
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
      value = "true";
      if ("".equals(requestVal) || requestVal == null) {
        value = "";
        checked = false;
      }
    }
    return new ElementResult(value, new StaticElementInfo(name, getDefault(checked), 1),
        ElementResult.NOCHILDS, Boolean.valueOf(checked));
  }


  public HTMLProducer getDefault(boolean checked) {
    return (pi) -> {
      ElementRenderer renderer = pi.getTheme().getRenderer();
      String aria = renderer.renderAriaDescribedBy(pi, pi.getDecoration());
      String val = renderer.renderValue(pi.getElementResult().getValue());
      String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"checkbox\" name=\""
          + pi.getNameOfInput() + "\" value" + val + (checked ? " checked" : "") + aria + ">";
      return renderer.renderInputFree(inputHtml, pi, pi.getDecoration(),
          ElementRenderer.InputVariant.checkbox);
    };
  }

  @Override
  public String toString() {
    return String.format("CheckBoxInput. name=%s", name);
  }

}
