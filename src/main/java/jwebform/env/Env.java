package jwebform.env;

// holds pointers to the web-env. (request, response, session, maybe headers...)
public class Env {
	Request request;

	public Env(Request request) {
		this.request = request;
	}

	public Request getRequest() {
		return request;
	}
}
