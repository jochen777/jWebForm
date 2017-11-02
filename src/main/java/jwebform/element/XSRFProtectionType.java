package jwebform.element;

import java.security.SecureRandom;
import java.util.Base64;
import com.coverity.security.Escape;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.HTMLProducer;
import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.validation.ValidationResult;

// Form-Elmement, that provides XSRF protection
public class XSRFProtectionType implements Element {

  private final String TOKENNAME = "tokenname";
  private final String TOKENVAL = "tokenVal";

  private final SecureRandom random = new SecureRandom();

  private final boolean staticTokenName;

  public XSRFProtectionType() {
    this(false);
  }

  // do not use staticTokenname = true in runtim. Use it only for testing
  public XSRFProtectionType(boolean staticTokenName) {
    this.staticTokenName = staticTokenName;

  }

  private String getRandomValue() {
    final byte[] bytes = new byte[32];
    random.nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  @Override
  public ElementResult apply(EnvWithSubmitInfo envWith) {
    Env env = envWith.getEnv();
    env.ensureSessionAvail();

    // ############ validation

    String name = env.getRequest().getParameter(TOKENNAME);
    String xsrfVal = env.getRequest().getParameter(TOKENVAL);

    ValidationResult tempValidationResult;

    boolean isSubmitted = env.getRequest().isSubmitted(TOKENVAL);
    boolean submittedValueEqualsSessionVal =
        isSubmitted ? xsrfVal.equals(env.getSessionGet().getAttribute(name)) : false;

    if (isSubmitted && !submittedValueEqualsSessionVal
    // && !staticTokenName
    ) {
      tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
    } else {
      tempValidationResult = ValidationResult.ok();
    }


    name = "token-" + (staticTokenName ? "" : Math.random());
    xsrfVal = (staticTokenName ? "static" : getRandomValue());
    env.getSessionSet().setAttribute(name, xsrfVal);

    // ###############

    // TODO: What happens, if session runs out and user want's a new code?

    // is firstrun - then generate a complete new token

    ElementResult result =
        new ElementResult("xsrf_protection", getRenderer(name, xsrfVal), tempValidationResult);

    return result; // no representation
  }

  public HTMLProducer getRenderer(String name, String xsrfVal) {
    return producerInfos -> {
      StringBuilder tags = new StringBuilder();

      tags.append("<input type=\"hidden\" name=\"" + TOKENNAME + "\" value=\""
          + Escape.htmlText(name) + "\">");
      tags.append("<input type=\"hidden\" name=\"" + TOKENVAL + "\" value=\""
          + Escape.htmlText(xsrfVal) + "\">\n");

      String rendererdHtml = tags.toString();

      String problemDescription = "";
      if (!producerInfos.getElementResult().getValidationResult().isValid) {
        problemDescription = "XSRF Problem!<br>"; // RFE: MAke this
                                                  // nicer/configurable!
      }
      return problemDescription + rendererdHtml;
    };
  }


}