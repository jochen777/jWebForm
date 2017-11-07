package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;

import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

// Renderer for bootstrap for common elements
public class BootstrapRenderer {

  private final ProducerInfos pi;
  private final OneFieldDecoration decoration;

  public BootstrapRenderer(ProducerInfos pi, OneFieldDecoration decoration) {
    this.pi = pi;
    this.decoration = decoration;
  }

  public String renderInput(String type) {
    String placeholder = generatePlaceholder();
    String aria = renderAriaDescribedBy();
    String val = renderValue();

    return renderInputFree("<input tabindex=\"" + pi.getTabIndex() + "\" type=\"" + type
        + "\" name=\"" + pi.getNameOfInput() + "\" value" + val + placeholder + aria + ">");
  }

  public String renderInputComplex(String tagname, String inBetweenHtml) {
    String placeholder = generatePlaceholder();

    String aria = renderAriaDescribedBy();
    return renderInputFree(
        "<" + tagname + " tabindex=\"" + pi.getTabIndex() + "\" name=\"" + pi.getNameOfInput()
            + "\"" + placeholder + aria + ">" + inBetweenHtml + "</" + tagname + ">");

  }

  public String renderInputFree(String free) {
    String errorClass = calculateErrorClass();

    String errorMessage = renderErrorMessage();

    String labelStr = generateLabel();

    String helpHTML = renderHelpText();

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr).append(free)
        .append(helpHTML).append("</div>\n").toString();

  }


  protected String calculateErrorClass() {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorClass = "";
    if (vr.isSuccess()) {
      errorClass = " has-success";
    } else if (vr.isError()) {
      errorClass = " has-error";
    }
    return errorClass;
  }

  protected String generateLabel() {
    String labelStr =
        "<label for=\"" + pi.getNameOfInput() + "\">" + decoration.getLabel() + ":</label>";
    return labelStr;
  }

  protected String generatePlaceholder() {
    String placeholder = "";
    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      placeholder = " placeholder=\"" + decoration.getPlaceholder() + "\"";
    }
    return placeholder;
  }


  protected boolean isHelpDesired() {
    return decoration.getHelptext() != OneFieldDecoration.EMPTY;
  }

  protected String renderHelpText() {
    String helpHTML;
    if (isHelpDesired()) {
      helpHTML = "<span id=\"helpBlock-" + pi.getNameOfInput() + "\" class=\"help-block\">"
          + decoration.getHelptext() + "</span>";
    } else {
      helpHTML = "";
    }
    return helpHTML;
  }

  protected String renderAriaDescribedBy() {
    String aria;
    if (this.isHelpDesired()) {
      aria = " aria-describedby=\"helpBlock-" + pi.getNameOfInput() + "\"";
    } else {
      aria = "";
    }
    return aria;
  }

  protected String renderValue() {
    String val = pi.getElementResult().getValue();
    if (val != null && val.length() > 0) {
      val = "=\"" + Escape.html(val) + "\"";
    }
    return val;
  }

  protected String renderErrorMessage() {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorMessage = "";
    if (vr.isError()) {
      errorMessage = "Problem: " + vr.getMessage() + "<br>";
    }
    return errorMessage;
  }


}
