package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;

import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.Wrapper;
import jwebform.validation.ValidationResult;

// Renderer for bootstrap for common elements
public class BootstrapRenderer implements ElementRenderer {


  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.renderer.bootstrap.ElementRenderer#renderInput(java.lang.String,
   * jwebform.element.structure.ProducerInfos, jwebform.element.structure.OneFieldDecoration)
   */
  @Override
  public String renderInput(String type, ProducerInfos pi, OneFieldDecoration decoration) {
    String placeholder = generatePlaceholder(decoration);
    String aria = renderAriaDescribedBy(pi, decoration);
    String val = renderValue(pi);

    return renderInputFree("<input tabindex=\"" + pi.getTabIndex() + "\" type=\"" + type
        + "\" name=\"" + pi.getNameOfInput() + "\" value" + val + placeholder + aria + ">", pi,
        decoration);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.renderer.bootstrap.ElementRenderer#renderInputComplex(java.lang.String,
   * java.lang.String, jwebform.element.structure.ProducerInfos,
   * jwebform.element.structure.OneFieldDecoration)
   */
  @Override
  public String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      OneFieldDecoration decoration) {
    String placeholder = generatePlaceholder(decoration);

    String aria = renderAriaDescribedBy(pi, decoration);
    return renderInputFree(
        "<" + tagname + " tabindex=\"" + pi.getTabIndex() + "\" name=\"" + pi.getNameOfInput()
            + "\"" + placeholder + aria + ">" + inBetweenHtml + "</" + tagname + ">",
        pi, decoration);

  }

  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.renderer.bootstrap.ElementRenderer#renderInputFree(java.lang.String,
   * jwebform.element.structure.ProducerInfos, jwebform.element.structure.OneFieldDecoration)
   */
  @Override
  public String renderInputFree(String free, ProducerInfos pi, OneFieldDecoration decoration) {
    String errorClass = calculateErrorClass(pi);

    String errorMessage = renderErrorMessage(pi);

    String labelStr = generateLabel(pi, decoration);

    String helpHTML = renderHelpText(pi, decoration);

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    
    String input = buf.append(errorClass).append("\">").append(errorMessage).append(labelStr).append(free)
        .append(helpHTML).append("</div>\n").toString();
    
    if (pi.getElementContainer() != null && pi.getElementContainer().behaviour != null) {
      return pi.getElementContainer().behaviour.getAllAround().wrap(input);
    }
    return input;

  }


  protected String calculateErrorClass(ProducerInfos pi) {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorClass = "";
    if (vr.isSuccess()) {
      errorClass = " has-success";
    } else if (vr.isError()) {
      errorClass = " has-error";
    }
    return errorClass;
  }

  protected String generateLabel(ProducerInfos pi, OneFieldDecoration decoration) {
    String labelStr =
        "<label for=\"" + pi.getNameOfInput() + "\">" + decoration.getLabel() + ":</label>";
    return labelStr;
  }

  protected String generatePlaceholder(OneFieldDecoration decoration) {
    String placeholder = "";
    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      placeholder = " placeholder=\"" + decoration.getPlaceholder() + "\"";
    }
    return placeholder;
  }


  protected boolean isHelpDesired(OneFieldDecoration decoration) {
    return decoration.getHelptext() != OneFieldDecoration.EMPTY;
  }

  protected String renderHelpText(ProducerInfos pi, OneFieldDecoration decoration) {
    String helpHTML;
    if (isHelpDesired(decoration)) {
      helpHTML = "<span id=\"helpBlock-" + pi.getNameOfInput() + "\" class=\"help-block\">"
          + decoration.getHelptext() + "</span>";
    } else {
      helpHTML = "";
    }
    return helpHTML;
  }

  @Override
  public String renderAriaDescribedBy(ProducerInfos pi, OneFieldDecoration decoration) {
    String aria;
    if (this.isHelpDesired(decoration)) {
      aria = " aria-describedby=\"helpBlock-" + pi.getNameOfInput() + "\"";
    } else {
      aria = "";
    }
    return aria;
  }

  @Override
  public String renderValue(ProducerInfos pi) {
    String val = pi.getElementResult().getValue();
    if (val != null && val.length() > 0) {
      val = "=\"" + Escape.html(val) + "\"";
    }
    return val;
  }

  protected String renderErrorMessage(ProducerInfos pi) {
    ValidationResult vr = pi.getElementResult().getValidationResult();
    String errorMessage = "";
    if (vr.isError()) {
      errorMessage = "Problem: " + vr.getMessage() + "<br>";
    }
    return errorMessage;
  }


}
