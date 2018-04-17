package jwebform.env;

/**
 * Holds the request-attributes - acts as a gateway to the servlet request for example
 * 
 * @author jochen
 *
 */
@FunctionalInterface
public interface Request {
  String getParameter(String name);

  default boolean isSubmitted(String name) {
    return getParameter(name) != null;
  }
}
