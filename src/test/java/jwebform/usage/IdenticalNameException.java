package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.field.HtmlType;
import jwebform.field.SimpleType;
import jwebform.field.TextType;
import jwebform.field.structure.Decoration;
import jwebform.env.EnvBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IdenticalNameException {

  @Test
  public void testIdenticalName() {
    try {
      Form f = FormBuilder.simple()
          .fields(new TextType("pete", "").of(new Decoration("Pete1")),
              new TextType("pete", "").of(new Decoration("Pete2")))
          .build();
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
      Form f = FormBuilder.simple().singleTypes(new SimpleType(), new SimpleType(),
          new HtmlType("Hello"), new HtmlType("world")).build();
      f.run(new EnvBuilder().of(t -> t));
      assertTrue("An exeption should not be raised before!", true);
    } catch (Exception e) {
      fail("An exeption should not be raised, because we have double SIMPLE elements!");
    }
  }
}
