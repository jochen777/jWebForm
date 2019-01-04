package jwebform.usage.validation;

import jwebform.validation.criteria.Criteria;
import jwebform.validation.criteria.Url;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestUrl {

  @Test
  public void testUrl() {
    Url urlValidation = (Url) Criteria.url();
    assertTrue(urlValidation.validate("http://www.test.de").isValid());
    assertTrue(urlValidation.validate("https://www.test.de").isValid());
    assertTrue(urlValidation.validate("httpadsf://www.test.de").isError());
    assertTrue(urlValidation.validate("http://www.test.de/lsafalert<").isError());
    assertTrue(urlValidation.validate("http://www.test.de/lsa?asdf=werwer").isValid());
  }
}
