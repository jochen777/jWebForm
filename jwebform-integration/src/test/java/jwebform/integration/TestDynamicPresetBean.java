package jwebform.integration;

import jwebform.FormModel;
import jwebform.field.SubmitType;
import jwebform.integration.bean2form.Bean2Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.bean2form.annotations.UseDecoration;
import jwebform.integration.bean2form.annotations.UseFieldType;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.fromBean.ExampleRequests;
import jwebform.resultprocessor.ModelResultProcessor;
import org.junit.Test;

import jakarta.validation.constraints.NotEmpty;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDynamicPresetBean {


  @Test
  public void testDynamicPresetBean() {
    Bean2Form bean2FromContract = getBean2Form();

    FormRunnerConfig formRunnerConfig =
      new FormRunnerConfig((a, b, c) -> "", bean2FromContract, new ModelResultProcessor(
        FormModel.Method.POST, FormModel.Html5Validation.ON), "form");

    FormRunner jwebform = new FormRunner(ExampleRequests.exampleSubmittedRequest("firstname", "Jochen"),
      ExampleRequests.emptySessionGet(), ExampleRequests.emptySessionPut(),
      ExampleRequests.stupidModel(), formRunnerConfig);

    DemoForm demoForm = new DemoForm("peter");
    FormResultAndBean fr = jwebform.runWithBean(demoForm);
    assertTrue(fr.isValid());
    assertEquals(
      "Jochen", demoForm.firstname);
  }


  private Bean2Form getBean2Form() {
    return new DefaultBean2Form(getBeanValidator(), getRuleDeliverer());
  }

  private BeanValidationRuleDeliverer getRuleDeliverer() {
    return (bean, name) -> {
      return Collections.emptySet();
    };
  }

  private BeanValidationValidator getBeanValidator() {
    return (b) -> {
      return Collections.emptyList();
    };
  }

  public static class DemoForm {

    public DemoForm(String firstname) {
      this.firstname = firstname;
    }

    @UseDecoration(label = "firstname.label", helpText = "firstname.helptext")
    public String firstname = "";

    @NotEmpty
    public String lastname = "";

    @UseFieldType(SubmitType.class)
    public String submit = "";
  }

}
