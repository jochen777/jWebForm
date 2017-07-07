package jwebform.element;

import java.security.SecureRandom;
import java.util.Base64;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.element.structure.RenderInfos;
import jwebform.element.structure.ValidationInfos;
import jwebform.env.Env;
import jwebform.validation.ValidationResult;

// Form-Elmement, that provides XSRF protection
public class XSRFProtection implements Element {

  private final String TOKENNAME = "tokenname";
  private final String TOKENVAL = "tokenVal";

  private final SecureRandom random = new SecureRandom();

  private final boolean staticTokenName;

  public XSRFProtection() {
    this(false);
  }

  // do not use staticTokenname = true in runtim. Use it only for testing
  public XSRFProtection(boolean staticTokenName) {
    this.staticTokenName = staticTokenName;

  }

  private String getRandomValue() {
    final byte[] bytes = new byte[32];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  @Override
  public ElementResult run(RenderInfos renderInfos) {
    Env env = renderInfos.getEnv();

    if (env.getSessionGet() == null || env.getSessionSet() == null) {
      throw new SessionMissingException();
    }

    // ############ validation

    String name = renderInfos.getEnv().getRequest().getParameter(TOKENNAME);
    String xsrfVal = renderInfos.getEnv().getRequest().getParameter(TOKENVAL);

    ValidationResult tempValidationResult;
    tempValidationResult = ValidationResult.ok();

    if (xsrfVal != null && !xsrfVal.equals(renderInfos.getEnv().getSessionGet().getAttribute(name))
        && !staticTokenName) {
      tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
    }


    // ###############

    // TODO: What happens, if session runs out and user want's a new code?

    // is firstrun - then generate a complete new token
    StringBuilder tags = new StringBuilder();

    name = "token-" + (staticTokenName ? "" : Math.random());
    xsrfVal = (staticTokenName ? "static" : getRandomValue());
    env.getSessionSet().setAttribute(name, xsrfVal);

    tags.append("<input type=\"hidden\" name=\"" + TOKENNAME + "\" value=\"" + Escape.htmlText(name)
        + "\">");
    tags.append("<input type=\"hidden\" name=\"" + TOKENVAL + "\" value=\""
        + Escape.htmlText(xsrfVal) + "\">\n");

    String rendererdHtml = tags.toString();

    ValidationResult validationResultToWorkWith = tempValidationResult;
    String problemDescription = "";
    if (!validationResultToWorkWith.isValid) {
      problemDescription = "XSRF Problem!<br>"; // RFE: MAke this
                                                // nicer/configurable!
    }
    ElementResult result = new ElementResult("xsrf_protection", new XsrfRenderer(),
        problemDescription + rendererdHtml, validationResultToWorkWith, "");

    return result; // no representation
  }

  public class XsrfRenderer implements HTMLProducer {


    public XsrfRenderer() {
    }

    @Override
    public String getHTML(ValidationResult vr) {
      StringBuilder tags = new StringBuilder();

      String name = "token-" + (staticTokenName ? "" : Math.random());
      String xsrfVal = (staticTokenName ? "static" : getRandomValue());

      tags.append("<input type=\"hidden\" name=\"" + TOKENNAME + "\" value=\""
          + Escape.htmlText(name) + "\">");
      tags.append("<input type=\"hidden\" name=\"" + TOKENVAL + "\" value=\""
          + Escape.htmlText(xsrfVal) + "\">\n");

      String rendererdHtml = tags.toString();

      String problemDescription = "";
      if (!vr.isValid) {
        problemDescription = "XSRF Problem!<br>"; // RFE: MAke this
                                                  // nicer/configurable!
      }
      return problemDescription + rendererdHtml;

    }

  }

  public class SessionMissingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SessionMissingException() {
      super(
          "Session data missing in Env. \nPlease provide sessionGet() and sessionSet() in Env!\n\n...This is needed for XSRF-Protection."
              + "\n\nExample: \nnew Env(requestParamName -> request.getParameter(requestParamName),	// Request"
              + "\nsessionParamName -> request.getSession().getAttribute(sessionParamName), // SessionGet"
              + "\n(sessionParamName, value) -> request.getSession().setAttribute(sessionParamName, value) // SessionSet"
              + "\n);");
    }
  }

  @Override
  public ValidationResult validate(ValidationInfos validationInfos) {
    String name = validationInfos.getEnv().getRequest().getParameter(TOKENNAME);
    String xsrfVal = validationInfos.getEnv().getRequest().getParameter(TOKENVAL);

    ValidationResult tempValidationResult;
    tempValidationResult = ValidationResult.ok();

    if (xsrfVal != null
        && !xsrfVal.equals(validationInfos.getEnv().getSessionGet().getAttribute(name))
        && !staticTokenName) {
      tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
    }
    return tempValidationResult;
  }

}
