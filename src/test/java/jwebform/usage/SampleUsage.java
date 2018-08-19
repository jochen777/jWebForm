package jwebform.usage;

import jwebform.FormResult;
import jwebform.View;
import jwebform.View.Html5Validation;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    View v = result.getView(Html5Validation.ON);
    assertEquals(true, v.isUploadEnctypeRequired());

    FormResult resultWithoutUpload = getFormResultWithoutUpload(env);
    View v2 = resultWithoutUpload.getView(Html5Validation.ON);
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
    FormResult result = getFormResult(env);

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
    FormResult result = getFormResult(env);

    ExpectedResultList expRes = new ExpectedResultList();
    expRes.add("xsrf_protection", true, "");
    expRes.add("", true, "");
    expRes.add("textInput", false, "");
    expRes.add("dateInput", true, "2017-07-04");
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
    expRes.add("dateInput", true, "2017-07-04");
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

  private FormResult getFormResult(Env env) {
    return new SampleFormBuilder(formId).buildForm().run(env);
  }

  private FormResult getFormResultWithoutUpload(Env env) {
    return new SampleFormBuilder(formId).buildWithoutUploadForm().run(env);
  }

  private class ExpectedResultList {
    List<ExpectedElementResult> expRes = new ArrayList<>();

    public void add(String name, boolean validationREsult, String value) {
      expRes.add(new ExpectedElementResult(name, validationREsult, value));
    }

    public List<ExpectedElementResult> getList() {
      return expRes;
    }
  }

  private class ExpectedElementResult {
    public ExpectedElementResult(String name, boolean vr, String value) {
      this.name = name;
      this.vr = vr;
      this.value = value;
    }

    final String name;
    final boolean vr;
    final String value;
  }

  private void testExpectectedResults(FormResult result,
      List<ExpectedElementResult> expectedResults) {

    assertEquals(expectedResults.size(), result.getElementResults().size());

    int i = 0;
    for (ElementContainer cont : result.getElementResults().getContainers()) {
      ElementResult eResult = result.getElementResults().get(cont);
      ExpectedElementResult expectedResult = expectedResults.get(i);
      assertEquals(expectedResult.name, eResult.getStaticElementInfo().getName());
      // System.err.println(eResult.getStaticElementInfo().getName());
      assertEquals("Type:" + cont.element.getClass().getName() + ": "
          + eResult.getStaticElementInfo().getName() + "/" + expectedResult.name + " expResult: "
          + expectedResult.vr + "/real:" + eResult.getValidationResult().isValid,
          eResult.getValidationResult().isValid, expectedResult.vr);

      assertTrue(
          eResult.getStaticElementInfo().getName() + "/" + expectedResult.name + " Value: "
              + expectedResult.value + "/" + eResult.getValue(),
          eResult.getValue().equals(expectedResult.value));
      i++;
    }
    assertEquals(formId, result.getFormId());


    View v = result.getView(Html5Validation.ON);
    assertEquals("POST", v.getMethod());
    assertEquals(true, v.isHtml5Validaiton());

    View v2 = result.getView(View.Method.GET);
    assertEquals("GET", v2.getMethod());
    assertEquals(true, v2.isHtml5Validaiton());

    View v3 = result.getView(Html5Validation.OFF, View.Method.POST);
    assertEquals("POST", v3.getMethod());
    assertEquals(false, v3.isHtml5Validaiton());


  }



}
