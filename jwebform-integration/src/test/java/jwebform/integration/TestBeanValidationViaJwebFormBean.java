package jwebform.integration;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.integration.bean2form.AbstractJWebFormBean;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.ExternalValidation;

public class TestBeanValidationViaJwebFormBean {

  // checks, if validation throw interface "JWebFormBean" works
  @Test
  public void test_beanValidationViajWebFormBeanInterface() {
    Form form = new DefaultBean2Form().getFormFromBean(new TestBean());
    FormResult fr =
        form.run(new EnvBuilder().of(ExampleRequests.exampleSubmittedRequest("password1", "xy")));
    assertTrue("Form should not be valid, because pw1 != pw2", !fr.isValid());

  }

  public class TestBean extends AbstractJWebFormBean {
    public String password1;
    public String password2;

    @Override
    public List<ExternalValidation> validate() {
      if (!password1.equals(password2)) {
        return generateSingleExternalValidation("password1", "Passwords are not equal");
      }
      return emptyExternalValidation();
    }
  }
}
