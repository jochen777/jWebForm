package jwebform.env;

// Builder for Env Objects. Can be overriden
public class EnvBuilder {

  private int maxLen = 1000;

  public EnvBuilder setMaxLen(int maxLen) {
    this.maxLen = maxLen;
    return this;
  }

  public Env of(Request request) {
    return of(request, Env.EMPTY_SESSION_GET, Env.EMPTY_SESSION_SET);
  }

  // typical env with nullchecks, trimming of input and maxlen of 1000
  public Env of(Request request, SessionGet sessionGet, SessionSet sessionSet) {
    return new Env(request, sessionGet, sessionSet).cloneWithNullCheck().cloneWithTrim()
        .cloneWithMaxLenInput(maxLen);
  }

}
