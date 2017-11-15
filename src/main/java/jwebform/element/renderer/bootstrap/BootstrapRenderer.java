package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;

import jwebform.element.structure.Behaviour;
import jwebform.element.structure.ElementRenderer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Required;
import jwebform.view.MessageSource;

// Renderer for bootstrap for common elements
public class BootstrapRenderer implements ElementRenderer {

  private final MessageSource messageSource;

  public BootstrapRenderer(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String renderInput(String type, ProducerInfos pi, OneFieldDecoration decoration) {
    String placeholder = generatePlaceholder(decoration);
    String aria = renderAriaDescribedBy(pi, decoration);
    String val = renderValue(pi);



    return renderInputFree("<input tabindex=\"" + pi.getTabIndex() + "\" type=\"" + type
        + "\" name=\"" + pi.getNameOfInput() + "\" value" + val + placeholder + aria
        + renderRequired(pi.getElementContainer().validator) + ">", pi, decoration);
  }

  protected String renderRequired(Validator validator) {
    if (validator.containsCriterion(Required.getInstance())) {
      return " required";
    }
    return "";
  }

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
            + "\"" + placeholder + aria + renderRequired(pi.getElementContainer().validator) + ">"
            + inBetweenHtml + "</" + tagname + ">",
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

    String input = buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(free).append(helpHTML).append("</div>\n").toString();

    for (Behaviour be : pi.getBehaviours()) {
      input = be.getAllAround().wrap(input);
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
    StringBuilder labelAppend = new StringBuilder(":");
    if (pi.getElementContainer().validator.containsCriterion(Required.getInstance())) {
      labelAppend.append(" *");
    }
    StringBuilder complete = new StringBuilder();
    return complete.append("<label for=\"").append(pi.getNameOfInput()).append("\">")
        .append(messageSource.getMessage(decoration.getLabel())).append(labelAppend)
        .append("</label>").toString();

  }


  protected String generatePlaceholder(OneFieldDecoration decoration) {
    String placeholder = "";
    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      placeholder =
          " placeholder=\"" + messageSource.getMessage(decoration.getPlaceholder()) + "\"";
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
          + messageSource.getMessage(decoration.getHelptext()) + "</span>";
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
      errorMessage = "Problem: " + messageSource.getMessage(vr.getMessage()) + "<br>";
    }
    return errorMessage;
  }


}
