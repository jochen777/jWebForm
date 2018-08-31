package jwebform.env;

/**
 * Environment that connects jWebform to the Web-Request, so this Env is between your webframework
 * and jWebform.
 *
 * Main objectives:
 * * Check if the form was submitted
 * * provide the request-parameters
 * * allow to write and read from the session.
 *
 *
 * Because Request and Session-Handling are just functional interfaces, you can
 * easily pass in some lambdas, that will surely match your web framework of choice.
 * (And even for unit-tests, it's great.) So it will work with HttpServletRequest as well with
 * a simple map, that will hold the input-params. (For example in Spring with
 *  @ RequestParam Map<String, String> params )
 *
 * You can not use this directly. Use the EnvBuilder to build an Env for you.
 *
 *
 */
public class Env {
  private final Request request;
  private final SessionGet sessionGet;
  private final SessionSet sessionSet;

  final static SessionGet EMPTY_SESSION_GET = t -> "";
  final static SessionSet EMPTY_SESSION_SET = (k, v) -> { };

  private final static String SUBMIT_KEY = "WF_SUBMITTED";
  private final static String SUBMIT_VALUE_PREFIX = "WF-";

  protected Env(Request request, SessionGet sessionGet, SessionSet sessionSet) {
    this.request = request;
    this.sessionGet = sessionGet;
    this.sessionSet = sessionSet;
  }

  /**
   * just for extensibility reasons: Maybe you want to enhance reqeust with header-infos, you can
   * pass in an extended Request Object (inherited from Request).
   *
   * But normally, it is enough to call the "getParameter" Method
    */

  public Request getRequest() {
    return request;
  }

  public boolean isSubmitted(String name) {
    return request.isSubmitted(name);
  }
  public String getParameter(String name) {
    return request.getParameter(name);
  }

  public Object getSessionAttribute(String attributeName) {
    return sessionGet.getAttribute(attributeName);
  }

  public void setSessionAttribute(String name, Object o){
    sessionSet.setAttribute(name, o);
  }

  public void ensureSessionAvail() {
    if (sessionGet == EMPTY_SESSION_GET || sessionSet == EMPTY_SESSION_SET) {
      throw new SessionMissingException();
    }
  }

  public class SessionMissingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    SessionMissingException() {
      super(
          "Session data missing in Env. \nPlease provide sessionGet() and sessionSet() in Env!\n\n...This is needed for XSRF-Protection."
              + "\n\nExample: \nnew Env(requestParamName -> request.getParameter(requestParamName),	// Request"
              + "\nsessionParamName -> request.getSession().getAttribute(sessionParamName), // SessionGet"
              + "\n(sessionParamName, value) -> request.getSession().setAttribute(sessionParamName, value) // SessionSet"
              + "\n);");
    }
  }

  public EnvWithSubmitInfo getEnvWithSumitInfo(String formId) {
    return new EnvWithSubmitInfo(formId, this);
  }


  public class EnvWithSubmitInfo {
    private final Env env;
    private final boolean submitted;

    public EnvWithSubmitInfo(String formId, Env env) {
      this.env = env;
      this.submitted =
          (Env.SUBMIT_VALUE_PREFIX + formId).equals(env.getParameter(Env.SUBMIT_KEY));
    }

    public Env getEnv() {
      return env;
    }

    public boolean isSubmitted() {
      return submitted;
    }

  }




}
