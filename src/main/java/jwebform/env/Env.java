package jwebform.env;


// holds pointers to the web-env. (request, response, session, maybe headers...)
public class Env {
	private final Request request;
	private final SessionGet sessionGet;
	private final SessionSet sessionSet;
	
	final static SessionGet EMPTY_SESSION_GET = t -> "";
	final static SessionSet EMPTY_SESSION_SET = (k,v) -> {};
	
	public Env(Request request) {
		this(request, EMPTY_SESSION_GET, EMPTY_SESSION_SET);
	}

	public Env(Request request, SessionGet sessionGet, SessionSet sessionSet) {
		this.request = request;
		this.sessionGet = sessionGet;
		this.sessionSet = sessionSet;
	}

	public Request getRequest() {
		return request;
	}

	public Env cloneWithFormIdPrefix(String formId) {
		return new Env(p -> request.getParameter(p), sessionGet, sessionSet); 
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

}
