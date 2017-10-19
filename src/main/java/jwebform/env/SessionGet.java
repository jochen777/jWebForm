package jwebform.env;

/**
 * Holds the session-attributes - acts as a gateway to the servlet request for example
 * @author jochen
 *
 */
@FunctionalInterface
public interface SessionGet {
	public Object getAttribute(String name);
}
