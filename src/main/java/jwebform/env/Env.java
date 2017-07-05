package jwebform.env;

// holds pointers to the web-env. (request, response, session, maybe headers...)
public class Env {
	private final Request request;
	private final SessionGet sessionGet;
	private final SessionSet sessionSet;

	public Env(Request request) {
		this(request, null, null);
	}

	public Env(Request request, SessionGet sessionGet, SessionSet sessionSet) {
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
	
}
