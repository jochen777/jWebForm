package jwebform.element;

import java.security.SecureRandom;
import java.util.Base64;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.Validateable;
import jwebform.env.Env;
import jwebform.validation.ValidationResult;

// Form-Elmement, that provides XSRF protection
public class XSRFProtection implements Validateable, Element{

	private final String TOKENNAME = "tokenname";
	private final String TOKENVAL = "tokenVal";
	
	private final ValidationResult validationResult;
	private final SecureRandom random = new SecureRandom();
	private final String rendererdHtml;
	

	public XSRFProtection(Env env) {
		
		// TODO: Add proper Errors, if env is "session-empty"!
		// TODO: What happens, if session runs out and user want's a new code?
		
		// is firstrun - then generate a complete new token
		StringBuilder tags = new StringBuilder();
		String name;
		String xsrfVal;


		name = env.getRequest().getParameter(TOKENNAME);
		xsrfVal = env.getRequest().getParameter(TOKENVAL);
		
		
		ValidationResult tempValidationResult;
		tempValidationResult = ValidationResult.ok();

		if (xsrfVal != null && !xsrfVal.equals(env.getSessionGet().getAttribute(name))) {
			tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
			}

		validationResult = tempValidationResult;
		
		name = "token-" + Math.random();
		xsrfVal = getRandomValue();
		env.getSessionSet().setAttribute(name, xsrfVal);

		tags.append("<input type=\"hidden\" name=\"" + TOKENNAME + "\" value=\"" + Escape.htmlText(name)
				+ "\">");
		tags.append("<input type=\"hidden\" name=\"" + TOKENVAL + "\" value=\"" + Escape.htmlText(xsrfVal)
				+ "\">\n");
		
		rendererdHtml = tags.toString();
	}

	private String getRandomValue() {
		final byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}

	
	
	@Override
	public ValidationResult getValidationResult() {
		return validationResult;
	}

	@Override
	public String getValue() {
		return "";	// no value (just ValidationResult)
	}

	@Override
	public String getHtml(int tabIndex, ValidationResult overridenValidationResult) {
		ValidationResult validationResultToWorkWith = overridenValidationResult==null?validationResult:overridenValidationResult;
		String problemDescription = "";
		if (!validationResultToWorkWith.isValid) {
			problemDescription = "XSRF Problem!<br>";	// RFE: MAke this nicer/configurable!
		}
		return problemDescription + rendererdHtml;	// no representation
	}

	
}
