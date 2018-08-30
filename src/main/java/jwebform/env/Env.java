package jwebform.env;

// holds pointers to the web-env. (request, response, session, maybe headers...)
public class Env {
  private final Request request;
  private final SessionGet sessionGet;
  private final SessionSet sessionSet;

  final static SessionGet EMPTY_SESSION_GET = t -> "";
  final static SessionSet EMPTY_SESSION_SET = (k, v) -> { };

  private final static String SUBMIT_KEY = "WF_SUBMITTED";
  public final static String SUBMIT_VALUE_PREFIX = "WF-";

  protected Env(Request request, SessionGet sessionGet, SessionSet sessionSet) {
    this.request = request;
    this.sessionGet = sessionGet;
    this.sessionSet = sessionSet;
  }

  public Request getRequest() {
    return request;
  }

  public SessionGet getSessionGet() {
    return sessionGet;
  }

  public SessionSet getSessionSet() {
    return sessionSet;
  }

  public void ensureSessionAvail() {
    if (getSessionGet() == EMPTY_SESSION_GET || getSessionSet() == EMPTY_SESSION_SET) {
      throw new SessionMissingException();
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

  public EnvWithSubmitInfo getEnvWithSumitInfo(String formId) {
    return new EnvWithSubmitInfo(formId, this);
  }

  // will deliver an Env, that cuts the input after some chars. Useful for security-reasons
  public Env cloneWithMaxLenInput(int maxLen) {
    return new Env((i) -> cutString(this.request.getParameter(i), maxLen), this.sessionGet,
        this.sessionSet);
  }

  public Env cloneWithNullCheck() {
    return new Env((i) -> nullSave(this.request.getParameter(i)), this.sessionGet, this.sessionSet);
  }

  public Env cloneWithTrim() {
    // make sure, that you choose before the cloneWithNullCheck
    return new Env((i) -> this.request.getParameter(i).trim(), this.sessionGet, this.sessionSet);
  }


  private String nullSave(String input) {
    if (input == null) {
      return "";
    } else {
      return input;
    }
  }

  private String cutString(String s, int len) {
    if (s == null) {
      return null;
    }
    if (s.length() < len) {
      return s;
    }
    return s.substring(0, len);
  }

  public class EnvWithSubmitInfo {
    private final Env env;
    private final boolean submitted;



    public EnvWithSubmitInfo(String formId, Env env) {
      this.env = env;
      this.submitted =
          (Env.SUBMIT_VALUE_PREFIX + formId).equals(env.getRequest().getParameter(Env.SUBMIT_KEY));
    }



    public Env getEnv() {
      return env;
    }


    public boolean isSubmitted() {
      return submitted;
    }

  }

}
