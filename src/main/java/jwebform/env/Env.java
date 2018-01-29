package jwebform.env;

import jwebform.processors.Processor;
import jwebform.view.StartEndRenderer;

// holds pointers to the web-env. (request, response, session, maybe headers...)
public class Env {
  private final Request request;
  private final SessionGet sessionGet;
  private final SessionSet sessionSet;

  final static SessionGet EMPTY_SESSION_GET = t -> "";
  final static SessionSet EMPTY_SESSION_SET = (k, v) -> {
  };

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

  public EnvWithSubmitInfo getEnvWithSumitInfo(String formId, Processor processor) {
    return new EnvWithSubmitInfo(formId, this, processor);
  }

  // will deliver an Env, that cuts the input after some chars. Useful for security-reasons
  public Env cloneWithMaxLenInput(int maxLen) {
    Env maxLenEnv = new Env((i) -> cutString(this.request.getParameter(i), maxLen), this.sessionGet,
        this.sessionSet);
    return maxLenEnv;
  }

  public Env cloneWithNullCheck() {
    Env maxLenEnv =
        new Env((i) -> nullSave(this.request.getParameter(i)), this.sessionGet, this.sessionSet);
    return maxLenEnv;
  }

  // RFE: Maybe better to cut just the trailing spaces
  public Env cloneWithTrim() {
    // make sure, that you choose before the cloneWithNullCheck
    Env trimmingEnv =
        new Env((i) -> this.request.getParameter(i).trim(), this.sessionGet, this.sessionSet);
    return trimmingEnv;
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
    private final Processor processor;




    public Processor getProcessor() {
      return processor;
    }



    public EnvWithSubmitInfo(String formId, Env env, Processor processor) {
      this.env = env;
      if ((StartEndRenderer.SUBMIT_VALUE_PREFIX + formId)
          .equals(env.getRequest().getParameter(StartEndRenderer.SUBMIT_KEY))) {
        this.submitted = true;
      } else {
        this.submitted = false;
      }
      this.processor = processor;
    }



    public Env getEnv() {
      return env;
    }


    public boolean isSubmitted() {
      return submitted;
    }



  }

}
