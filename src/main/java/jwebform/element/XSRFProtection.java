package jwebform.element;

import java.security.SecureRandom;
import java.util.Base64;

import com.coverity.security.Escape;

import jwebform.element.structure.Element;
import jwebform.element.structure.ElementResult;
import jwebform.element.structure.RenderInfos;
import jwebform.env.Env;
import jwebform.validation.ValidationResult;

// Form-Elmement, that provides XSRF protection
public class XSRFProtection implements Element{

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
		// TODO: What happens, if session runs out and user want's a new code?
		
		// is firstrun - then generate a complete new token
		StringBuilder tags = new StringBuilder();
		String name;
		String xsrfVal;


		name = env.getRequest().getParameter(TOKENNAME);
		xsrfVal = env.getRequest().getParameter(TOKENVAL);
		
		
		ValidationResult tempValidationResult;
		tempValidationResult = ValidationResult.ok();

		if (xsrfVal != null && !xsrfVal.equals(env.getSessionGet().getAttribute(name)) && !staticTokenName) {
			tempValidationResult = ValidationResult.fail("formchecker.xsrf_problem");
			}

		ValidationResult validationResult = tempValidationResult;
		
		name = "token-" + (staticTokenName?"":Math.random());
		xsrfVal = (staticTokenName?"static":getRandomValue());
		env.getSessionSet().setAttribute(name, xsrfVal);

		tags.append("<input type=\"hidden\" name=\"" + TOKENNAME + "\" value=\"" + Escape.htmlText(name)
				+ "\">");
		tags.append("<input type=\"hidden\" name=\"" + TOKENVAL + "\" value=\"" + Escape.htmlText(xsrfVal)
				+ "\">\n");
		
		String rendererdHtml = tags.toString();
		
		ValidationResult validationResultToWorkWith = renderInfos.getOverrideValidationResult()==ValidationResult.undefined()?validationResult:renderInfos.getOverrideValidationResult();
		String problemDescription = "";
		if (!validationResultToWorkWith.isValid) {
			problemDescription = "XSRF Problem!<br>";	// RFE: MAke this nicer/configurable!
		}
		ElementResult result = new ElementResult("xsrf_protection", problemDescription + rendererdHtml, validationResultToWorkWith, "");

		return result;	// no representation
	}

	
	public class SessionMissingException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public SessionMissingException(){
			super("Session data missing in Env. \nPlease provide sessionGet() and sessionSet() in Env!\n\n...This is needed for XSRF-Protection."
					+ "\n\nExample: \nnew Env(requestParamName -> request.getParameter(requestParamName),	// Request"
					+ "\nsessionParamName -> request.getSession().getAttribute(sessionParamName), // SessionGet"
					+ "\n(sessionParamName, value) -> request.getSession().setAttribute(sessionParamName, value) // SessionSet"
					+ "\n);");
		}
	}
	
}
