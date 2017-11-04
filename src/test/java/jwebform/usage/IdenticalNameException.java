package jwebform.usage;

import org.junit.Test;
import jwebform.Form;
import jwebform.element.TextType;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.env.Env;
import jwebform.validation.Validator;
import static org.junit.Assert.assertTrue;

public class IdenticalNameException {
  
  @Test
  public void testIdenticalName() {
    try {
      Form f = new Form("id", new TextType("pete", new OneFieldDecoration("Pete1"), "", new Validator()),
          new TextType("pete", new OneFieldDecoration("Pete2"), "", new Validator())
          );
      f.run(new Env(t -> t), true);
      assertTrue("An exeption should be raised before!", false);  // fail, because the run should raise the exception
    } catch (Exception e) {
        // fine, an Exception should be thrown here, because we added two same fields.
    }
  }
}
