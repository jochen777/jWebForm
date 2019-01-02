package jwebform.resultprocessor;

/**
 * Whith this logger interface you can "inject" Logging Mechanisms as you need it.
 */
@FunctionalInterface
public interface Logger {

  public void log(String msg);
}
