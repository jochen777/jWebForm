package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.element.CheckBoxType;
import jwebform.element.HiddenType;
import jwebform.element.HtmlType;
import jwebform.element.LabelType;
import jwebform.element.NumberType;
import jwebform.element.PasswordType;
import jwebform.element.RadioType;
import jwebform.element.SelectType;
import jwebform.element.SimpleType;
import jwebform.element.SubmitType;
import jwebform.element.TextAreaType;
import jwebform.element.TextDateType;
import jwebform.element.TextType;
import jwebform.element.UploadType;
import jwebform.element.XSRFProtectionType;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.ElementResult;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

public class SampleUsage {

  // TODO: Test if complete form is valid!!
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
    return new MyFormBuilder().buildForm().run(env);
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
    for (ElementContainer cont : result.getElementResults().keySet()) {
      ElementResult eResult = result.getElementResults().get(cont);
      ExpectedElementResult expectedResult = expectedResults.get(i);
      assertEquals(expectedResult.name, eResult.getStaticElementInfo().getName());
      //System.err.println(eResult.getStaticElementInfo().getName());
      assertEquals(
        eResult.getStaticElementInfo().getName() + "/" + expectedResult.name + " expResult: "
          + expectedResult.vr + "/real:" + eResult.getValidationResult().isValid,
        eResult.getValidationResult().isValid, expectedResult.vr);

      assertTrue(
          eResult.getStaticElementInfo().getName() + "/" + expectedResult.name + " Value: "
              + expectedResult.value + "/" + eResult.getValue(),
          eResult.getValue().equals(expectedResult.value));
      i++;
    }
  }

  public class MyFormBuilder {

    XSRFProtectionType protection = new XSRFProtectionType(true); // no random values, so we can
                                                                  // expect

    Validator required = new Validator(Criteria.required());

    ElementContainer textInput = new ElementContainer(new TextType("textInput", "Peter\"Paul"),
        required, new Decoration("TextInputLabel"));

    ElementContainer date = new TextDateType("dateInput", LocalDate.of(2017, 7, 4)).of(required,
        new Decoration("Please insert date", "datehelptext"));
    ElementContainer textInput2 = new TextType("textInput2", "Peter\"Paul").of(required,
        new Decoration("TextInputLabel2", "Help-Text", "Placeholder"));
    ElementContainer gender = new ElementContainer(
        new SelectType("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"}),
        Validator.emptyValidator(), new Decoration("Gender"));
    ElementContainer chk = new ElementContainer(new CheckBoxType("chk", true), required,
        new Decoration("chk-label", "chk_help"));
    ElementContainer lbl = new LabelType("lbl").of();
    ElementContainer html = new HtmlType("<strong>HTML</strong>").of();
    ElementContainer hddn = new HiddenType("hddn", "hddn-value").of();
    ElementContainer area = new TextAreaType("area", "Area-Prebuild").of(required,
        new Decoration("Area", "Area-Help", "Area-Placeholder"));

    ElementContainer nmbr = new ElementContainer(new NumberType("nbr", 5), required,
        new Decoration("chk-label", "chk_help"));
    ElementContainer pssword = new ElementContainer(new PasswordType("pssword"),
        Validator.emptyValidator(), new Decoration("Password"));
    ElementContainer upld = new UploadType("upld").of(new Decoration("Upload"));
    ElementContainer radio = new ElementContainer(
        new RadioType("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"}),
        Validator.emptyValidator(), new Decoration("Radio"));

    /*
     * radio, (OK)
     */

    public MyFormBuilder() {}

    public LocalDate getDateValue(FormResult formResult) {
      return (LocalDate) formResult.getElementResults().get(date).getValueObject();
    }

    public Form buildForm() {
      List<FormValidator> formValidators = new ArrayList<>();
      formValidators.add(it -> {
        final Map<ElementContainer, ValidationResult> overridenValidationResults = new HashMap<>();
        String valueOfTextInput = it.get(textInput).getValue();
        if (valueOfTextInput.length() > 3) {
          overridenValidationResults.put(textInput, ValidationResult.fail("not_ok"));
        }
        return overridenValidationResults;
      });

      // test here field-apis

      return new Form(formId, formValidators, protection.of(), new SimpleType().of(), textInput,
          date, textInput2, gender, new SubmitType("Submit").of(), chk, lbl, html, hddn, area, nmbr,
          pssword, upld, radio);

    }


  }

}
