package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.env.EnvBuilder;
import jwebform.usage.helper.ExampleRequests;
import jwebform.validation.criteria.Criteria;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static jwebform.field.builder.BuildInType.text;

// test, if submitted info is coming correctly
public class FormSubmitTest {

  public static final String FIRSTNAME = "firstname";

  private Form buildForm() {
    return FormBuilder.simple().typeBuilder(text(FIRSTNAME).criteria(Criteria.required())).build();
  }

  @Test
  public void test_EmptySubmit() {
    Form form = buildForm();
    FormResult fr = form.run(new EnvBuilder().of(ExampleRequests.notSubmittedRequest()));
    assertTrue("request does not have submit info, so submitted should be false",
        !fr.isSubmitted());
    assertTrue("... should not be submitted", !fr.isValid() && !fr.isSubmitted());
  }

  @Test
  public void test_SubmitOK() {
    Form form = buildForm();
    FormResult fr = form
        .run(new EnvBuilder().of(ExampleRequests.exampleSubmittedRequest(FIRSTNAME, "Testname")));
    assertTrue("Form should be submitted", fr.isSubmitted());
    assertTrue("Form should be validated and ok", fr.isValid());
  }

  @Test
  public void test_SubmitNotOK() {
    Form form = buildForm();
    FormResult fr =
        form.run(new EnvBuilder().of(ExampleRequests.exampleSubmittedRequest(FIRSTNAME, "")));
    assertTrue("Form should be submitted", fr.isSubmitted());
    assertTrue("Form should submitted, but not okay.", !fr.isValid());
  }

}
