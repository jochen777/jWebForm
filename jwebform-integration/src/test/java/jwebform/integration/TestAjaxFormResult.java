package jwebform.integration;

import static org.junit.Assert.assertEquals;
import javax.validation.constraints.NotEmpty;
import org.junit.Test;
import jwebform.Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.fromBean.ExampleRequests;
import jwebform.processor.FormResultBuilder;

public class TestAjaxFormResult {

  @Test
  public void testAjaxFormResult() {
    Form f = new DefaultBean2Form().getFormFromBean(new MyFormRequired());

    FormResultBuilder ajaxFormResultBuilder = AjaxFormResult::new;
    AjaxFormResult fr = (AjaxFormResult) f.run(
        ExampleRequests.fromRequest(ExampleRequests.exampleSubmittedRequest("name", "jochen")),
        ajaxFormResultBuilder);
    assertEquals("success", fr.getAjaxResult().status);
  }


  public class MyFormRequired {
    @NotEmpty
    public String name;
  }
}
