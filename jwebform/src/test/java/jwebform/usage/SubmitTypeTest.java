package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.usage.helper.ExampleRequests;
import org.junit.Test;

import static jwebform.field.builder.BuildInType.submit;
import static org.junit.Assert.assertEquals;

public class SubmitTypeTest {

  @Test
  public void testSubmit() {
    String valueOfSubmit = "1";
    String submitFieldName = "sbm";


    Form testForm = FormBuilder.simple().typeBuilder(submit(submitFieldName)).build();

    FormResult formResult = testForm.run(new EnvBuilder().of(
      ExampleRequests.exampleSubmittedRequest(submitFieldName, valueOfSubmit)));
    assertEquals(valueOfSubmit, formResult.getStringValue(submitFieldName));
  }

}
