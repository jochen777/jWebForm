package jwebform.usage;

import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvBuilderTest {
  @Test
  public void test_cutLenghtOfInput() {
    String input = "1234567890";
    EnvBuilder envBuilder = new EnvBuilder();
    envBuilder.setMaxLen(5);
    Env env = envBuilder.of(t -> t);
    assertEquals("12345", env.getParameter(input));
  }

  @Test
  public void test_nullValues() {
    String input = null;
    Env env = new EnvBuilder().of(t -> t);
    assertEquals("", env.getParameter(input));
  }

  @Test
  public void test_trim() {
    String input = "  hello   ";
    Env env = new EnvBuilder().of(t -> t);
    assertEquals("hello", env.getParameter(input));
  }


}
