package jwebform.themes.sourcecode;

import java.util.Optional;
import com.coverity.security.Escape;
import jwebform.field.structure.Decoration;
import jwebform.integration.MessageSource;
import jwebform.model.ProducerInfos;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.MaxLength;

// Renderer for bootstrap for common elements
public class BootstrapRenderer implements ElementRenderer {

  private final MessageSource messageSource;

  public BootstrapRenderer(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String renderInput(String type, ProducerInfos pi, Decoration decoration,
      String additional) {
    String placeholder = generatePlaceholder(decoration);
    String aria = renderAriaDescribedBy(pi, decoration);
    String val = renderValue(pi.getValue());

    // TODO: id must be dynamic!
    return renderInputFree("<input id=\"form-id-" + pi.getName() + "\" class=\"form-control "
        + calculateErrorClass(pi) + "\" tabindex=\"" + pi.getTabIndex() + "\" type=\"" + type
        + "\" name=\"" + pi.getName() + "\" value" + val + placeholder + aria
        + renderRequired(pi.getValidator()) + renderMaxLen(pi.getValidator()) + additional + ">",
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
  public String renderInputComplex(String tagname, String inBetweenHtml, ProducerInfos pi,
      Decoration decoration) {
    String placeholder = generatePlaceholder(decoration);

    String aria = renderAriaDescribedBy(pi, decoration);
    return renderInputFree(
        "<" + tagname + " class=\"form-control\" tabindex=\"" + pi.getTabIndex() + "\" name=\""
            + pi.getName() + "\"" + placeholder + aria + renderRequired(pi.getValidator())
            + renderMaxLen(pi.getValidator()) + ">" + inBetweenHtml + "</" + tagname + ">",
        pi, decoration, ElementRenderer.InputVariant.normal);

  }

  /*
   * (non-Javadoc)
   * 
   * @see jwebform.element.renderer.bootstrap.ElementRenderer#renderInputFree(java.lang.String,
   * jwebform.model.ProducerInfos, jwebform.element.structure.OneFieldDecoration)
   */
  @Override
  public String renderInputFree(String free, ProducerInfos pi, Decoration decoration,
      ElementRenderer.InputVariant variant) {
    String wrapperClass;
    PlaceWhereToRenderLabel placeWhereToRenderLabel = PlaceWhereToRenderLabel.overInput;
    switch (variant) {
      case radio:
        wrapperClass = "radio";
        placeWhereToRenderLabel = PlaceWhereToRenderLabel.outside;
        break;
      case checkbox:
        wrapperClass = getGroupClass() + " form-check";
        placeWhereToRenderLabel = PlaceWhereToRenderLabel.underInput;
        break;
      default:
        wrapperClass = getGroupClass();
    }
    return renderInputFree(free, pi, decoration, wrapperClass, placeWhereToRenderLabel);
  }


  private String renderInputFree(String free, ProducerInfos pi, Decoration decoration,
      String classNameWrapper, PlaceWhereToRenderLabel placeWhereToRenderLabel) {
    String errorMessage = renderErrorMessage(pi);

    String labelStr = generateLabel(pi, decoration);

    String helpHTML = renderHelpText(pi, decoration);

    StringBuilder buf = new StringBuilder();
    Wrapper wrapAroundCompleteInfo = getWrapper(pi, classNameWrapper);
    if (placeWhereToRenderLabel == PlaceWhereToRenderLabel.outside) {
      Wrapper labelWrapper = new Wrapper("<div class=\"" + getGroupClass() + "\">", "</div>");
      buf.append(labelWrapper.start).append(labelStr).append(labelWrapper.end).append(free)
          .append(errorMessage).append(helpHTML).append("\n");
    } else if (placeWhereToRenderLabel == PlaceWhereToRenderLabel.overInput) {
      buf.append(wrapAroundCompleteInfo.start).append(labelStr).append(free).append(errorMessage)
          .append(helpHTML).append(wrapAroundCompleteInfo.end).append("\n");
    } else if (placeWhereToRenderLabel == PlaceWhereToRenderLabel.underInput) {
      buf.append(wrapAroundCompleteInfo.start).append(free).append(errorMessage).append(labelStr)
          .append(helpHTML).append(wrapAroundCompleteInfo.end).append("\n");
    }

    String input = buf.toString();


    return input;

  }

  protected String getGroupClass() {
    return "form-group";
  }

  private Wrapper getWrapper(ProducerInfos pi, String className) {
    return new Wrapper("<div class=\"" + className + "\">", "</div>");
  }

  @Override
  public String calculateErrorClass(ProducerInfos pi) {
    ValidationResult vr = pi.getValidationResult();
    String errorClass = "";
    if (vr.isSuccess()) {
      errorClass = "is-valid";
    } else if (vr.isError()) {
      errorClass = "is-invalid";
    }
    return errorClass;
  }

  protected String generateLabel(ProducerInfos pi, Decoration decoration) {
    StringBuilder labelAppend = new StringBuilder();
    if (pi.getValidator().isRequired()) {
      labelAppend.append(" *");
    }
    StringBuilder complete = new StringBuilder();
    return complete.append("<label class=\"control-label\" for=\"")
        .append(pi.getFormId() + "-" + pi.getName()).append("\">")
        .append(decoration.isTranlated() ? decoration.getLabel()
            : messageSource.getMessage(decoration.getLabel()))
        .append(labelAppend).append("</label>").toString();

  }


  protected String generatePlaceholder(Decoration decoration) {
    String placeholder = "";
    if (!decoration.getPlaceholder().isEmpty()) {
      placeholder = " placeholder=\"" + (decoration.isTranlated() ? decoration.getPlaceholder()
          : messageSource.getMessage(decoration.getPlaceholder())) + "\"";
    }
    return placeholder;
  }


  protected boolean isHelpDesired(Decoration decoration) {
    return !decoration.getHelptext().isEmpty();
  }

  protected String renderHelpText(ProducerInfos pi, Decoration decoration) {
    String helpHTML;
    if (isHelpDesired(decoration)) {
      helpHTML = "\n<small id=\"" + pi.getFormId() + "-" + pi.getName()
          + "-help_block\" class=\"form-text text-muted\">\n"
          + (decoration.isTranlated() ? decoration.getHelptext()
              : messageSource.getMessage(decoration.getHelptext()))
          + "\n</small>\n";
    } else {
      helpHTML = "";
    }
    return helpHTML;
  }

  @Override
  public String renderAriaDescribedBy(ProducerInfos pi, Decoration decoration) {
    String aria;
    if (this.isHelpDesired(decoration)) {
      aria = " aria-describedby=\"helpBlock-" + pi.getName() + "\"";
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

  @Override
  public MessageSource getMessageSource() {
    return messageSource;
  }

  @Override
  public String renderErrorMessage(ProducerInfos pi) {
    ValidationResult vr = pi.getValidationResult();
    String errorMessage = "";
    if (vr.isError()) {
      if (StringUtils.isEmpty(vr.getMessageKey())) {
        errorMessage = vr.getTranslatedMessage();
      } else {
        errorMessage = messageSource.getMessage(vr.getMessageKey());
      }
      return "<div class=\"invalid-feedback\">" + errorMessage + "</div>";
    }
    return "";
  }

  @Override
  public String renderSimpleLabel(String forAttribute, String label) {
    return "<label class=\"control-label\" for=\"" + forAttribute + "\">" + label + "</label>\n";
  }

  enum PlaceWhereToRenderLabel {
    outside, overInput, underInput
  }

}
