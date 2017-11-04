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

  public String calculateErrorClass() {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorClass = "";
    if (vr.isSuccess()) {
      errorClass = " has-success";
    } else if (vr.isError()) {
      errorClass = " has-error";
    }
    return errorClass;
  }

  public String generateLabel() {
    String labelStr =
        "<label for=\"" + pi.getNameOfInput() + "\">" + decoration.getLabel() + ":</label>";
    return labelStr;
  }

  public String generatePlaceholder() {
    String placeholder = "";
    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      placeholder = " placeholder=\"" + decoration.getPlaceholder() + "\"";
    }
    return placeholder;
  }


  public boolean isHelpDesired() {
    return decoration.getHelptext() != OneFieldDecoration.EMPTY;
  }

  public String renderHelpText() {
    String helpHTML;
    if (isHelpDesired()) {
      helpHTML = "<span id=\"helpBlock-" + pi.getNameOfInput() + "\" class=\"help-block\">"
          + decoration.getHelptext() + "</span>";
    } else {
      helpHTML = "";
    }
    return helpHTML;
  }

  public String renderAriaDescribedBy() {
    String aria;
    if (this.isHelpDesired()) {
      aria = " aria-describedby=\"helpBlock-" + pi.getNameOfInput() + "\"";
    } else {
      aria = "";
    }
    return aria;
  }

  public String renderValue() {
    String val = pi.getElementResult().getValue();
    if (val != null && val.length() > 0) {
      val = "=\"" + Escape.html(val) + "\"";
    }
    return val;
  }

  public String renderErrorMessage() {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorMessage = "";
    if (vr.isError()) {
      errorMessage = "Problem: " + vr.getMessage() + "<br>";
    }
    return errorMessage;
  }

}
