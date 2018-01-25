package jwebform.view.theme;

import java.util.Optional;
import com.coverity.security.Escape;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.element.structure.behaviour.Behaviour;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.MaxLength;
import jwebform.view.ElementRenderer;
import jwebform.view.MessageSource;
import jwebform.view.Wrapper;

// Renderer for bootstrap for common elements
public class BootstrapRenderer implements ElementRenderer {

  private final MessageSource messageSource;

  public BootstrapRenderer(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String renderInput(
      String type,
      ProducerInfos pi,
      Decoration decoration,
      String additional) {
    String placeholder = generatePlaceholder(decoration);
    String aria = renderAriaDescribedBy(pi, decoration);
    String val = renderValue(pi.getElementResult().getValue());



    return renderInputFree(
        "<input class=\"form-control\" tabindex=\"" + pi.getTabIndex() + "\" type=\"" + type
            + "\" name=\"" + pi.getNameOfInput() + "\" value" + val + placeholder + aria
            + renderRequired(pi.getElementContainer().validator)
            + renderMaxLen(pi.getElementContainer().validator) + additional + ">",
        pi, decoration, ElementRenderer.InputVariant.normal);
  }

  private String renderMaxLen(Validator validator) {
    Optional<MaxLength> maxLen = validator.getMaxLen();
    if (maxLen.isPresent()) {
      return " maxlength=\"" + maxLen.get().getMaxLength() + "\"";
    }
    return "";
  }

  protected String renderRequired(Validator validator) {
    if (validator.isRequired()) {
      return " required";
    }
    return "";
  }

  @Override
  public String renderInputComplex(
      String tagname,
      String inBetweenHtml,
      ProducerInfos pi,
      Decoration decoration) {
    String placeholder = generatePlaceholder(decoration);

    String aria = renderAriaDescribedBy(pi, decoration);
    return renderInputFree("<" + tagname + " class=\"form-control\" tabindex=\"" + pi.getTabIndex()
        + "\" name=\"" + pi.getNameOfInput() + "\"" + placeholder + aria
        + renderRequired(pi.getElementContainer().validator)
        + renderMaxLen(pi.getElementContainer().validator) + ">" + inBetweenHtml + "</" + tagname
        + ">", pi, decoration, ElementRenderer.InputVariant.normal);

  }

  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.renderer.bootstrap.ElementRenderer#renderInputFree(java.lang.String,
   * jwebform.element.structure.ProducerInfos, jwebform.element.structure.OneFieldDecoration)
   */
  @Override
  public String renderInputFree(
      String free,
      ProducerInfos pi,
      Decoration decoration,
      ElementRenderer.InputVariant variant) {
    String wrapperClass;
    boolean renderLabelOutside = false;
    switch (variant) {
      case radio:
        wrapperClass = "radio";
        renderLabelOutside = true;
        break;
      case checkbox:
        wrapperClass = "form-check";
        break;
      default:
        wrapperClass = getGroupClass();
    }
    return renderInputFree(free, pi, decoration, wrapperClass, renderLabelOutside);
  }


  private String renderInputFree(
      String free,
      ProducerInfos pi,
      Decoration decoration,
      String classNameWrapper,
      boolean renderLabelOutside) {
    String errorMessage = renderErrorMessage(pi);

    String labelStr = generateLabel(pi, decoration);

    String helpHTML = renderHelpText(pi, decoration);

    StringBuffer buf = new StringBuffer();
    Wrapper wrapAroundCompleteInfo = getWrapper(pi, classNameWrapper);
    if (renderLabelOutside) {
      Wrapper labelWrapper = new Wrapper(
          "<div class=\"" + getGroupClass() + " " + calculateErrorClass(pi) + "\">", "</div>");
      buf.append(labelWrapper.start).append(errorMessage).append(labelStr).append(labelWrapper.end)
          .append(free).append(helpHTML).append("\n");
    } else {
      buf.append(wrapAroundCompleteInfo.start).append(errorMessage).append(labelStr).append(free)
          .append(helpHTML).append(wrapAroundCompleteInfo.end).append("\n");
    }

    String input = buf.toString();

    for (Behaviour be : pi.getBehaviours()) {
      input = be.getAllAround().wrap(input);
    }
    return input;

  }

  protected String getGroupClass() {
    return "form-group";
  }

  private Wrapper getWrapper(ProducerInfos pi, String className) {
    String errorClass = calculateErrorClass(pi);
    return new Wrapper("<div class=\"" + className + errorClass + "\">", "</div>");
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

  protected String generateLabel(ProducerInfos pi, Decoration decoration) {
    StringBuilder labelAppend = new StringBuilder(":");
    if (pi.getElementContainer().validator.isRequired()) {
      labelAppend.append(" *");
    }
    StringBuilder complete = new StringBuilder();
    return complete.append("<label class=\"control-label\" for=\"").append(pi.getNameOfInput())
        .append("\">").append(messageSource.getMessage(decoration.getLabel())).append(labelAppend)
        .append("</label>").toString();

  }


  protected String generatePlaceholder(Decoration decoration) {
    String placeholder = "";
    if (decoration.getPlaceholder() != Decoration.EMPTY) {
      placeholder =
          " placeholder=\"" + messageSource.getMessage(decoration.getPlaceholder()) + "\"";
    }
    return placeholder;
  }


  protected boolean isHelpDesired(Decoration decoration) {
    return decoration.getHelptext() != Decoration.EMPTY;
  }

  protected String renderHelpText(ProducerInfos pi, Decoration decoration) {
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
  public String renderAriaDescribedBy(ProducerInfos pi, Decoration decoration) {
    String aria;
    if (this.isHelpDesired(decoration)) {
      aria = " aria-describedby=\"helpBlock-" + pi.getNameOfInput() + "\"";
    } else {
      aria = "";
    }
    return aria;
  }

  @Override
  public String renderValue(String val) {
    if (val.length() > 0) {
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

  @Override
  public String renderSimpleLabel(String forAttribute, String label) {
    return "<label class=\"control-label\" for=\"" + forAttribute + "\">" + label + "</label>\n";
  }


}
