package jwebform.env;

/**
 * Holds the request-attributes - acts as a gateway to the servlet request for example
 * 
 */
@FunctionalInterface
public interface Request {
  String getParameter(String name);

  default boolean isSubmitted(String name) {
    // RFE: Is this correct? Or better call isEmpty? (remmber: The request filter always avoids nulls!)
    return getParameter(name) != null;
  }

  default Request andThen(Request after) {
    return (t) -> after.getParameter(getParameter(t));
  }

}
