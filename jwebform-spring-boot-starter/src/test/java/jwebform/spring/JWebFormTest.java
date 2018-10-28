package jwebform.spring;


import static org.junit.Assert.assertTrue;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import org.junit.Test;
import jwebform.FormResult;
import jwebform.themes.FormRenderer;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

public class JWebFormTest {

  // test if bean-validation works in JWebForm
  @Test
  public void testRun_beanValidation() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    JWebFormProperties properties = new JWebFormProperties();
    FormRenderer formRenderer = new ThemeJavaRenderer(
        new StandardMapper(jwebform.themes.sourcecode.BootstrapTheme.instance(msg -> msg)));

    JWebForm jwebform = new JWebForm(ExampleRequests.exampleSubmittedRequest("name", "test"),
        ExampleRequests.emptySessionGet(), ExampleRequests.emptySessionPut(),
        ExampleRequests.stupidModel(), validator, properties, formRenderer);

    FormResult fr = jwebform.run(new MyForm10());
    assertTrue(
        "The form should be not okay, beause the validation should fail ('test' is smaller than 10 chars)",
        !fr.isOk());

    FormResult fr2 = jwebform.run(new MyForm2());
    assertTrue("The form should be okay, 'test' is bigger than 2 chars", fr2.isOk());

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
