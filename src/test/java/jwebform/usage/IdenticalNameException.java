package jwebform.usage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import jwebform.Form;
import jwebform.element.HtmlType;
import jwebform.element.SimpleType;
import jwebform.element.TextType;
import jwebform.element.structure.Decoration;
import jwebform.env.EnvBuilder;

public class IdenticalNameException {

  @Test
  public void testIdenticalName() {
    try {
      Form f = new Form("id", new TextType("pete", "").of(new Decoration("Pete1")),
          new TextType("pete", "").of(new Decoration("Pete2")));
      f.run(new EnvBuilder().of(t -> t));
      fail("An exeption should be raised before!"); // fail, because the run should
                                                                 // raise the exception
    } catch (Exception e) {
      // fine, an Exception should be thrown here, because we added two same fields.
    }
  }

  @Test
  public void testDoubleSimpleElements() {
    try {
      Form f = new Form("id", new SimpleType(), new SimpleType(), new HtmlType("Hello"),
          new HtmlType("world"));
      f.run(new EnvBuilder().of(t -> t));
      assertTrue("An exeption should not be raised before!", true);
    } catch (Exception e) {
      fail("An exeption should not be raised, because we have double SIMPLE elements!");
    }
  }
}
