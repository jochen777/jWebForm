package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import jwebform.FormResult;
import jwebform.FormModel;
import jwebform.FormModel.Html5Validation;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.field.structure.Field;
import jwebform.field.structure.FieldResult;
import jwebform.processor.LoggingFormResult;

public class SampleUsage {

  String formId = "fid";


  @Test
  public void testnormalUsageFirstRun() {

    Env env = new EnvBuilder().of(it -> null, // this simulates the first run (all values null)
        t -> t, (k, v) -> {
        });
    FormResult result = getFormResult(env);

    ExpectedResultList expRes = new ExpectedResultList();
    expRes.add("xsrf_protection", true, "");
    expRes.add("", true, "");
    expRes.add("textInput", false, "Peter\"Paul");
    expRes.add("dateInput", false, "");
    expRes.add("textInput2", false, "Peter\"Paul");
    expRes.add("gender", false, "");
    expRes.add("submit", false, "");
    expRes.add("chk", false, "true");
    expRes.add("", true, "");
    expRes.add("", true, "");
    expRes.add("hddn", false, "hddn-value");
    expRes.add("area", false, "Area-Prebuild");
    expRes.add("nbr", false, "5");
    expRes.add("pssword", false, "");
    expRes.add("upld", false, "");
    expRes.add("radio", false, "1");

    testExpectectedResults(result, expRes.getList());


    assertTrue("The form should be not true, because it is the firstrun", !result.isOk());
  }


  @Test
  public void testUploadtype() {

    Env env = new EnvBuilder().of(it -> null, // this simulates the first run (all values null)
        t -> t, (k, v) -> {
        });
    FormResult result = getFormResult(env);
    FormModel v = result.getFormModel(Html5Validation.ON);
    assertEquals(true, v.isUploadEnctypeRequired());

    FormResult resultWithoutUpload = getFormResultWithoutUpload(env);
    FormModel v2 = resultWithoutUpload.getFormModel(Html5Validation.ON);
    assertEquals(false, v2.isUploadEnctypeRequired());


  }


  @Test
  public void testnormalUsageSubmitSuccess() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      if (it.equals("gender")) {
        return "m";
      }
      return "1";

    }, // this simulates the input of the names
        t -> t, (k, v) -> {
        });
    LoggingFormResult result = getFormResult(env);
    // result.logForm(System.err::print);

    ExpectedResultList expRes = new ExpectedResultList();
    expRes.add("xsrf_protection", true, "");
    expRes.add("", true, "");
    expRes.add("textInput", true, "1");
    expRes.add("dateInput", true, "0001-01-01");
    expRes.add("textInput2", true, "1");
    expRes.add("gender", true, "m");
    expRes.add("submit", true, "");
    expRes.add("chk", true, "true");
    expRes.add("", true, "");
    expRes.add("", true, "");
    expRes.add("hddn", true, "1");
    expRes.add("area", true, "1");
    expRes.add("nbr", true, "1");
    expRes.add("pssword", true, "1");
    expRes.add("upld", true, "1");
    expRes.add("radio", true, "1");
    testExpectectedResults(result, expRes.getList());

    assertTrue("The form should be true, because the inputs are ok", result.isOk());
  }

  @Test
  public void testnormalUsageSubmitError() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      // everything is empty
      return "";

    }, // this simulates empty inputs
        t -> t, (k, v) -> {
        });
    LoggingFormResult result = getFormResult(env);
    result.logForm(System.err::print);

    ExpectedResultList expRes = new ExpectedResultList();
    expRes.add("xsrf_protection", true, "");
    expRes.add("", true, "");
    expRes.add("textInput", false, "");
    expRes.add("dateInput", false, "");
    expRes.add("textInput2", false, "");
    expRes.add("gender", true, "");
    expRes.add("submit", true, "");
    expRes.add("chk", false, "");
    expRes.add("", true, "");
    expRes.add("", true, "");
    expRes.add("hddn", true, "");
    expRes.add("area", false, "");
    expRes.add("nbr", false, "");
    expRes.add("pssword", true, "");
    expRes.add("upld", true, "");
    expRes.add("radio", true, "");
    testExpectectedResults(result, expRes.getList());

    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result.isOk());
  }


  @Test
  public void testnormalUsageSubmitVarious() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      if (("textInput").equals(it)) {
        return "1";
      } else
        return "";

    }, t -> t, (k, v) -> {
    });
    FormResult result = getFormResult(env);

    ExpectedResultList expRes = new ExpectedResultList();
    expRes.add("xsrf_protection", true, "");
    expRes.add("", true, "");
    expRes.add("textInput", true, "1");
    expRes.add("dateInput", false, "");
    expRes.add("textInput2", false, "");
    expRes.add("gender", true, "");
    expRes.add("submit", true, "");
    expRes.add("chk", false, "");
    expRes.add("", true, "");
    expRes.add("", true, "");
    expRes.add("hddn", true, "");
    expRes.add("area", false, "");
    expRes.add("nbr", false, "");
    expRes.add("pssword", true, "");
    expRes.add("upld", true, "");
    expRes.add("radio", true, "");
    testExpectectedResults(result, expRes.getList());
    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result.isOk());
  }

  private LoggingFormResult getFormResult(Env env) {
    return (LoggingFormResult) new SampleFormBuilder(formId).buildForm().run(env);
  }

  private FormResult getFormResultWithoutUpload(Env env) {
    return new SampleFormBuilder(formId).buildWithoutUploadForm().run(env);
  }

  private class ExpectedResultList {
    List<ExpectedFieldResult> expRes = new ArrayList<>();

    public void add(String name, boolean validationREsult, String value) {
      expRes.add(new ExpectedFieldResult(name, validationREsult, value));
    }

    public List<ExpectedFieldResult> getList() {
      return expRes;
    }
  }

  private class ExpectedFieldResult {
    public ExpectedFieldResult(String name, boolean vr, String value) {
      this.name = name;
      this.vr = vr;
      this.value = value;
    }

    final String name;
    final boolean vr;
    final String value;
  }

  private void testExpectectedResults(FormResult result,
      List<ExpectedFieldResult> expectedResults) {

    assertEquals(expectedResults.size(), result.getFieldResults().size());

    int i = 0;
    for (Field cont : result.getFieldResults().getContainers()) {
      FieldResult eResult = result.getFieldResults().get(cont);
      ExpectedFieldResult expectedResult = expectedResults.get(i);
      assertEquals(expectedResult.name, eResult.getStaticFieldInfo().getName());
      assertEquals(
          "BuildInType:" + cont.fieldType.getClass().getName() + ": "
              + eResult.getStaticFieldInfo().getName() + "/" + expectedResult.name + " expResult: "
              + expectedResult.vr + "/real:" + eResult.getValidationResult().isValid,
          eResult.getValidationResult().isValid, expectedResult.vr);

      assertTrue(
          eResult.getStaticFieldInfo().getName() + "/" + expectedResult.name + " Value: "
              + expectedResult.value + "/" + eResult.getValue(),
          eResult.getValue().equals(expectedResult.value));
      i++;
    }
    assertEquals(formId, result.getFormId());


    FormModel v = result.getFormModel(Html5Validation.ON);
    assertEquals("POST", v.getMethod());
    assertEquals(true, v.isHtml5Validaiton());

    FormModel v2 = result.getFormModel(FormModel.Method.GET);
    assertEquals("GET", v2.getMethod());
    assertEquals(true, v2.isHtml5Validaiton());

    FormModel v3 = result.getFormModel(Html5Validation.OFF, FormModel.Method.POST);
    assertEquals("POST", v3.getMethod());
    assertEquals(false, v3.isHtml5Validaiton());


  }



}
