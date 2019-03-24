package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.usage.helper.ExampleRequests;
import jwebform.validation.criteria.Criteria;
import org.junit.Test;

import static jwebform.field.builder.BuildInType.submit;
import static jwebform.field.builder.BuildInType.text;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailValidationTest {

  @Test
  public void testEmail() {
    String valueOfSubmit = "bin@home.com";
    String submitFieldName = "email";


    Form testForm = FormBuilder.simple().typeBuilder(
      text(submitFieldName).criteria(Criteria.required(), Criteria.email())
    ).build();

    FormResult formResult = testForm.run(new EnvBuilder().of(
      ExampleRequests.exampleSubmittedRequest(submitFieldName, valueOfSubmit)));
    assertTrue(formResult.isValid());
    assertEquals(valueOfSubmit, formResult.getStringValue(submitFieldName));
  }
}
