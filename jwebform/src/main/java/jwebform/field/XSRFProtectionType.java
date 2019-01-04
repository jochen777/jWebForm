package jwebform.field;

import jwebform.env.Env;
import jwebform.env.Env.EnvWithSubmitInfo;
import jwebform.field.structure.FieldResult;
import jwebform.field.structure.HTMLProducer;
import jwebform.field.structure.SingleFieldType;
import jwebform.field.structure.StaticFieldInfo;
import jwebform.validation.ValidationResult;

import java.security.SecureRandom;
import java.util.Base64;

// Form-Elmement, that provides XSRF protection
public class XSRFProtectionType implements SingleFieldType {

  private static final String TOKENNAME = "tokenname";
  private static final String TOKENVAL = "tokenVal";

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
  public FieldResult apply(EnvWithSubmitInfo envWith) {
    Env env = envWith.getEnv();
    env.ensureSessionAvail();

    // ############ validation

    String xsrfVal = env.getParameter(TOKENVAL);

    ValidationResult tempValidationResult;

    boolean isSubmitted = env.isSubmitted(TOKENVAL);
    boolean submittedValueEqualsSessionVal = isSubmitted && xsrfVal
        .equals(env.getSessionAttribute(env.getParameter(TOKENNAME)));

    if (isSubmitted && !submittedValueEqualsSessionVal
    // && !staticTokenName
    ) {
      tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
    } else {
      tempValidationResult = ValidationResult.ok();
    }


    String newName = "token-" + (staticTokenName ? "" : Math.random());
    xsrfVal = (staticTokenName ? "static" : getRandomValue());
    env.setSessionAttribute(newName, xsrfVal);

    // ###############

    // is firstrun - then generate a complete new token

    // return new FieldResult("xsrf_protection", getRenderer(newName, xsrfVal),
    // tempValidationResult); // no representation
    return FieldResult.builder().withValue("")
        .withStaticFieldInfo(
            new StaticFieldInfo("xsrf_protection", getRenderer(newName, xsrfVal), 0))
        .withValidationResult(tempValidationResult).build();
  }

  public HTMLProducer getRenderer(String name, String xsrfVal) {
    return producerInfos -> {
      StringBuilder tags = new StringBuilder();

      tags.append("<input type=\"hidden\" name=\"").append(TOKENNAME).append("\" value=\"")
          .append(name).append("\">");
      tags.append("<input type=\"hidden\" name=\"").append(TOKENVAL).append("\" value=\"")
          .append(xsrfVal).append("\">\n");

      String rendererdHtml = tags.toString();

      String problemDescription = "";
      if (!producerInfos.getValidationResult().isValid) {
        problemDescription = "XSRF Problem!<br>"; // RFE: MAke this
                                                  // nicer/configurable!
      }
      return problemDescription + rendererdHtml;
    };
  }


}
