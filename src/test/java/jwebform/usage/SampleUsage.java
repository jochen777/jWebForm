package jwebform.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
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
import jwebform.element.structure.behaviour.AbstractBehaviour;
import jwebform.env.Env;
import jwebform.env.EnvBuilder;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;
import jwebform.view.Wrapper;
import jwebform.view.theme.BootstrapTheme;

public class SampleUsage {

  // TODO: Test if complete form is valid!!
  String formId = "fid";

  SimpleTemplate template = new SimpleTemplate();

  @Test
  public void testnormalUsageFirstRun() {

    Env env = new EnvBuilder().of(it -> null, // this simulates the first run (all values null)
        t -> t, (k, v) -> {
        });
    FormResult result = getFormResult(env);
    assertEquals(16, result.getElementResults().size());

    List<ExpectedElementResult> expectedResults = new ArrayList<>();
    expectedResults.add(new ExpectedElementResult("xsrf_protection", true, ""));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("textInput", false, "Peter\"Paul"));
    expectedResults.add(new ExpectedElementResult("dateInput", false, ""));
    expectedResults.add(new ExpectedElementResult("textInput2", false, "Peter\"Paul"));
    expectedResults.add(new ExpectedElementResult("gender", false, ""));
    expectedResults.add(new ExpectedElementResult("submit", false, ""));
    expectedResults.add(new ExpectedElementResult("chk", false, "true"));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("hddn", true, ""));
    expectedResults.add(new ExpectedElementResult("area", false, "Area-Prebuild"));
    expectedResults.add(new ExpectedElementResult("nbr", false, "5"));
    expectedResults.add(new ExpectedElementResult("pssword", false, ""));
    expectedResults.add(new ExpectedElementResult("upld", false, ""));
    expectedResults.add(new ExpectedElementResult("radio", false, "1"));
    testExpectectedResults(result, expectedResults);

    assertTrue("The form should be not true, because it is the firstrun", !result.isOk());
  }



  @Test
  public void testnormalUsageSubmitSuccess() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      return "1";

    }, // this simulates the input of the names
        t -> t, (k, v) -> {
        });
    FormResult result = getFormResult(env);
    assertEquals(16, result.getElementResults().size());

    List<ExpectedElementResult> expectedResults = new ArrayList<>();
    expectedResults.add(new ExpectedElementResult("xsrf_protection", true, ""));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("textInput", true, "1"));
    expectedResults.add(new ExpectedElementResult("dateInput", true, "0001-01-01"));
    expectedResults.add(new ExpectedElementResult("textInput2", true, "1"));
    expectedResults.add(new ExpectedElementResult("gender", true, "")); // TODO ?
    expectedResults.add(new ExpectedElementResult("submit", true, ""));
    expectedResults.add(new ExpectedElementResult("chk", true, "true"));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("", true, ""));
    expectedResults.add(new ExpectedElementResult("hddn", true, "")); // ?
    expectedResults.add(new ExpectedElementResult("area", true, "1"));
    expectedResults.add(new ExpectedElementResult("nbr", true, "1"));
    expectedResults.add(new ExpectedElementResult("pssword", true, "1"));
    expectedResults.add(new ExpectedElementResult("upld", true, "1"));
    expectedResults.add(new ExpectedElementResult("radio", true, "1")); // ?
    testExpectectedResults(result, expectedResults);

    assertTrue("The form should be true, because the inputs are ok", result.isOk());
  }

  @Test
  public void testnormalUsageSubmitError() {
    Env env = new EnvBuilder().of(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      return "";

    }, // this simulates empty inputs
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_error.html");
    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result);
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
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_various.html");
    assertTrue("The form should be false, because some fields are required or reqire a number",
        !result);
  }

  private FormResult getFormResult(Env env) {
    return new MyFormBuilder().buildForm().run(env);
  }


  private boolean testFormAgainstRequest(Env env, String templateName) {
    MyFormBuilder formBuilder = new MyFormBuilder();
    Form f = formBuilder.buildForm();
    FormResult result = f.run(env);

    String filecontent;
    try {
      filecontent = this.template.loadAndProcessTempalte(templateName);
      assertEquals(filecontent.trim(),
          result.getView(BootstrapTheme.instance(msg -> msg)).getHtml().trim());
      // System.err.println("Date: " + formBuilder.getDateValue(result));
      return result.isOk();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !result.isOk();
  }

  private class ExpectedElementResult {
    public ExpectedElementResult(String name, boolean vr, String value, Object valueObject) {
      this.name = name;
      this.vr = vr;
      this.value = value;
      this.valueObject = valueObject;
    }

    public ExpectedElementResult(String name, boolean vr, String value) {
      this.name = name;
      this.vr = vr;
      this.value = value;
      this.valueObject = value;
    }

    final String name;
    final boolean vr;
    final String value;
    final Object valueObject;



  }

  private void testExpectectedResults(FormResult result,
      List<ExpectedElementResult> expectedResults) {
    int i = 0;
    for (ElementContainer cont : result.getElementResults().keySet()) {
      ElementResult eResult = result.getElementResults().get(cont);
      ExpectedElementResult expectedResult = expectedResults.get(i);
      assertEquals(expectedResult.name, eResult.getStaticElementInfo().getName());
      System.err.println(eResult.getStaticElementInfo().getName());
      assertTrue(eResult.getStaticElementInfo().getName() + "/" + expectedResult.name + " Result: "
          + expectedResult.vr, eResult.getValidationResult().isValid == expectedResult.vr);

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
    // constant html
    AbstractBehaviour testBe = new AbstractBehaviour() {

      @Override
      public Wrapper getAllAround() {
        return new Wrapper("BEFORE", "AFTER\n");
      }

      // @Override
      // public Wrapper wrapLabel(ProducerInfos pi) {
      // if (pi.getElementContainer().validator.containsCriterion(Criteria.required())) {
      // return Wrapper.of("", " *");
      // } else {
      // return Wrapper.empty();
      // }
      // }
      //

    };

    ElementContainer textInput = new ElementContainer(new TextType("textInput", "Peter\"Paul"),
        new Validator(Criteria.required()), testBe, new Decoration("TextInputLabel"));

    ElementContainer date = new TextDateType("dateInput", LocalDate.of(2017, 7, 4)).of(
        new Validator(Criteria.required()), new Decoration("Please insert date", "datehelptext"));
    ElementContainer textInput2 =
        new TextType("textInput2", "Peter\"Paul").of(new Validator(Criteria.required()),
            new Decoration("TextInputLabel2", "Help-Text", "Placeholder"));
    ElementContainer gender = new ElementContainer(
        new SelectType("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"}),
        Validator.emptyValidator(), new Decoration("Gender"));
    ElementContainer chk = new ElementContainer(new CheckBoxType("chk", true),
        new Validator(Criteria.required()), new Decoration("chk-label", "chk_help"));
    ElementContainer lbl = new LabelType("lbl").of();
    ElementContainer html = new HtmlType("<strong>HTML</strong>").of();
    ElementContainer hddn = new HiddenType("hddn", "hddn-value").of();
    ElementContainer area =
        new TextAreaType("area", "Area-Prebuild").of(new Validator(Criteria.required()),
            new Decoration("Area", "Area-Help", "Area-Placeholder"));

    ElementContainer nmbr = new ElementContainer(new NumberType("nbr", 5),
        new Validator(Criteria.required()), new Decoration("chk-label", "chk_help"));
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

      Form f = new Form(formId, formValidators, new ElementContainer(protection),
          new ElementContainer(new SimpleType()), textInput, date, textInput2, gender,
          new SubmitType("Submit").of(), chk, lbl, html, hddn, area, nmbr, pssword, upld, radio);

      return f;

    }


  }

}
