package jwebform.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.field.builder.BuildInType;
import jwebform.integration.fromBean.ExampleRequests;
import jwebform.validation.criteria.Criteria;

public class TestAjaxFormResult {

  @Test
  public void testAjaxFormResultSuccess() {
    Form f = new MyFormRequired().build();

    FormResult fr = f.run(ExampleRequests.fromRequest(
        ExampleRequests.exampleSubmittedRequest("name", "pier", "firstname", "jochen")));
    AjaxResult ajaxResult = fr.process(new AjaxResultProcessor());
    assertEquals("success", ajaxResult.status);
    assertTrue(ajaxResult.data.size() == 0);
  }

  @Test
  public void testAjaxFormResultFail() {
    Form f = new MyFormRequired().build();

    FormResult fr = f.run(ExampleRequests
        .fromRequest(ExampleRequests.exampleSubmittedRequest("name", "pier", "firstname", "")));
    AjaxResult ajaxResult = fr.process(new AjaxResultProcessor());
    assertEquals("fail", ajaxResult.status);
    assertTrue(ajaxResult.data.size() == 1);
    assertEquals("jwebform.required", ajaxResult.data.get("firstname"));
  }



  public class MyFormRequired {

    public Form build() {
      return FormBuilder.simple()
          .typeBuilder(BuildInType.text("name").criteria(Criteria.required()),
              BuildInType.text("firstname").criteria(Criteria.required())

          ).build();

    }


  }
}
