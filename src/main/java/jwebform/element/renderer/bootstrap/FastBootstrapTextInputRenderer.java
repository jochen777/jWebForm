package jwebform.element.renderer.bootstrap;

import com.coverity.security.Escape;
import jwebform.element.TextType;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.element.structure.ProducerInfos;
import jwebform.validation.ValidationResult;

public class FastBootstrapTextInputRenderer implements HTMLProducer {

  @Override
  public String getHTML(ProducerInfos pi) {
    OneFieldDecoration decoration = ((TextType) pi.getElementResult().getSource()).decoration;
    String errorMessage = "";
    ValidationResult vr = pi.getElementResult().getValidationResult();


    String errorClass = "";
    if (vr != ValidationResult.undefined() && vr.isValid) {
      errorClass = " has-success";
    }
    if (vr != ValidationResult.undefined() && !vr.isValid) {
      errorClass = " has-error";
      errorMessage = "Problem: " + vr.getMessage() + "<br>";
    }

    String labelStr =
        "<label for=\"" + pi.getNameOfInput() + "\">" + decoration.getLabel() + ":</label>";

    // <input tabindex="5" type="text" name="fid-textInput2" value="Peter&quot;Paul"
    // placeholder="Placeholder" aria-describedby="helpBlock-textInput2">
    String placeholder = "";
    if (decoration.getPlaceholder() != OneFieldDecoration.EMPTY) {
      placeholder = " placeholder=\"" + decoration.getPlaceholder() + "\"";
    }

    String helpHTML;
    String aria;
    if (decoration.getHelptext() != OneFieldDecoration.EMPTY) {
      helpHTML = "<span id=\"helpBlock-" + pi.getNameOfInput() + "\" class=\"help-block\">"
          + decoration.getHelptext() + "</span>";
      aria = " aria-describedby=\"helpBlock-" + pi.getNameOfInput() + "\"";
    } else {
      helpHTML = "";
      aria = "";
    }
    String val = pi.getElementResult().getValue();
    if (val.length() > 0) {
      val = "=\"" + Escape.html(val) + "\"";
    }
    String inputHtml = "<input tabindex=\"" + pi.getTabIndex() + "\" type=\"text\" name=\""
        + pi.getNameOfInput() + "\" value" + val + placeholder + aria + ">";

    StringBuffer buf = new StringBuffer("<div class=\"form-group");
    return buf.append(errorClass).append("\">").append(errorMessage).append(labelStr)
        .append(inputHtml).append(helpHTML).append("</div>\n").toString();
  }

}
