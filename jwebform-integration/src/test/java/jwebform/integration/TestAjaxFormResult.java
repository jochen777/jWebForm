package jwebform.integration;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.fromBean.ExampleRequests;
import org.junit.Test;

import javax.validation.constraints.NotEmpty;

import static org.junit.Assert.assertEquals;

public class TestAjaxFormResult {

  @Test
  public void testAjaxFormResult() {
    Form f = new DefaultBean2Form().getFormFromBean(new MyFormRequired());

    FormResult fr = f.run(
        ExampleRequests.fromRequest(ExampleRequests.exampleSubmittedRequest("name", "jochen")));
    AjaxResult ajaxResult = fr.process(new AjaxResultProcessor());
    assertEquals("success", ajaxResult.status);
  }


  public class MyFormRequired {
    @NotEmpty
    public String name;
  }
}
