package jwebform.spring;


import static org.junit.Assert.assertTrue;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import org.junit.Test;
import jwebform.FormResult;

public class JWebFormTest {

  // test if bean-validation works in JWebForm
  @Test
  public void testRun() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    JWebForm jwebform = new JWebForm(ExampleRequests.exampleSubmittedRequest("name", "test"),
        ExampleRequests.emptySessionGet(), ExampleRequests.emptySessionPut(),
        ExampleRequests.stupidModel(), validator);

    FormResult fr = jwebform.run(new MyForm10());
    assertTrue("The form should be not okay, beause the validation should fail", !fr.isOk());

    FormResult fr2 = jwebform.run(new MyForm2());
    assertTrue("The form should be okay, test is bigger than 2 chars", fr2.isOk());

  }


  public class MyForm10 {
    @Size(min = 10, max = 200, message = "The name must be between 10 and 200 chars")
    public String name = "";
  }

  public class MyForm2 {
    @Size(min = 2, max = 200, message = "The name must be between 10 and 200 chars")
    public String name = "";
  }

}
