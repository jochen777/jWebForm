package jwebform.env;

/**
 * Holds the session-attributes - acts as a gateway to the servlet request for example
 * @author jochen
 *
 */
@FunctionalInterface
public interface SessionSet {
	public void setAttribute(String name, Object o);
}
