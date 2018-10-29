package jwebform.env;

/**
 * Build an Env out of Request (and optional SessionGet/SessionSet lambdas)
 *
 * It will add the the following filters to the reqeust:
 * * Null-Check (prevents null values dropping from extern into jWebform (jWebform tries to avoid using null))
 * * Trims the input (that means: Leading and traling spaces will be deleted from the input !!
 * * Set a maxlen of the input. This makes for security reasons sence. (Don't allow to post users 2 meg of data...)
 *
 * You can override this class to implement your own filters to the input. But you should always use the Nullcheck, to
 * ensure, that no null is getting in...
 */
public class EnvBuilder {

  private int maxLen = 50000;

  public EnvBuilder setMaxLen(int maxLen) {
    this.maxLen = maxLen;
    return this;
  }

  public Env of(Request request) {
    return of(request, Env.EMPTY_SESSION_GET, Env.EMPTY_SESSION_SET);
  }

  // typical env with nullchecks, trimming of input and maxlen of 50000
  public Env of(Request request, SessionGet sessionGet, SessionSet sessionSet) {
    return new Env(request.andThen(nullCheck).andThen(maxLenCutting).andThen(trimming) , sessionGet, sessionSet);
  }

  private Request trimming = String::trim;

  private Request nullCheck = input -> {
    if (input == null) {
      return "";
    } else {
      return input;
    }
  };

  private Request maxLenCutting = s -> {
    if (s == null) {
      return null;
    }
    if (s.length() < maxLen) {
      return s;
    }
    return s.substring(0, maxLen);
  };


}
