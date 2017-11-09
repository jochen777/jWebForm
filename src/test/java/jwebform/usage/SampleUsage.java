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
import jwebform.element.renderer.bootstrap.BootstrapTheme;
import jwebform.element.structure.Element;
import jwebform.element.structure.ElementContainer;
import jwebform.element.structure.OneFieldDecoration;
import jwebform.env.Env;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

public class SampleUsage {

  // TODO: Test if complete form is valid!!
  String formId = "fid";

  SimpleTemplate template = new SimpleTemplate();

  @Test
  public void testnormalUsageFirstRun() {

    Env env = new Env(it -> null, // this simulates the first run (all values null)
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_firstrun.html");
    assertTrue("The form should be false, because it is the firstrun", !result);
  }


  @Test
  public void testnormalUsageSubmitSuccess() {
    Env env = new Env(it -> {
      if (it.equals("WF_SUBMITTED")) {
        return "WF-fid";
      }
      return "1";

    }, // this simulates the input of the names
        t -> t, (k, v) -> {
        });
    boolean result = testFormAgainstRequest(env, "test/expectedHTMLExampleForm_submitted.html");
    assertTrue("The form should be true, because input-fields should be okay", result);
  }

  @Test
  public void testnormalUsageSubmitError() {
    Env env = new Env(it -> {
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
    Env env = new Env(it -> {
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


  private boolean testFormAgainstRequest(Env env, String templateName) {
    MyFormBuilder formBuilder = new MyFormBuilder();
    Form f = formBuilder.buildForm();
    FormResult result = f.run(env);
    String filecontent = this.template.loadAndProcessTempalte(templateName);
    assertEquals(filecontent.trim(), result.getView().getHtml(new BootstrapTheme()).trim());
    // System.err.println("Date: " + formBuilder.getDateValue(result));
    return result.isOk();
  }


  public class MyFormBuilder {

    XSRFProtectionType protection = new XSRFProtectionType(true); // no random values, so we can
                                                                  // expect
    // constant html

    ElementContainer textInput =
        new TextType("textInput", new OneFieldDecoration("TextInputLabel"), "Peter\"Paul")
            .of(new Validator(Criteria.required()));

    TextDateType date = new TextDateType("dateInput",
        new OneFieldDecoration("Please insert date", "datehelptext"), LocalDate.of(2017, 7, 4));
    ElementContainer textInput2 = new TextType("textInput2",
        new OneFieldDecoration("TextInputLabel2", "Help-Text", "Placeholder"), "Peter\"Paul")
            .of(new Validator(Criteria.required()));
    ElementContainer gender =
        new ElementContainer(new SelectType("gender", new OneFieldDecoration("Gender"), "",
            new String[] {"m", "f"}, new String[] {"Male", "Female"}));
    ElementContainer chk = new ElementContainer(
        new CheckBoxType("chk", new OneFieldDecoration("chk-label", "chk_help"), true),
        new Validator(Criteria.required()));
    ElementContainer lbl = new ElementContainer(new LabelType("lbl"));
    ElementContainer html = new HtmlType("<strong>HTML</strong>").of();
    ElementContainer hddn = new HiddenType("hddn", "hddn-value").of();
    ElementContainer area =
        new TextAreaType("area", new OneFieldDecoration("Area", "Area-Help", "Area-Placeholder"),
            "Area-Prebuild").of(new Validator(Criteria.required()));

    ElementContainer nmbr =
        new NumberType("nbr", new OneFieldDecoration("chk-label", "chk_help"), 5)
            .of(new Validator(Criteria.required()));
    ElementContainer pssword = new PasswordType("pssword", new OneFieldDecoration("Password")).of();
    ElementContainer upld = new UploadType("upld", new OneFieldDecoration("Upload")).of();
    ElementContainer radio = new RadioType("radio", new OneFieldDecoration("Radio"), "1",
        new String[] {"1", "2"}, new String[] {"yes", "no"}).of();

    /*
     * radio, (OK)
     */

    public MyFormBuilder() {

    }

    public LocalDate getDateValue(FormResult formResult) {
      return (LocalDate) formResult.getElementResults().get(date).getValueObject();
    }

    public Form buildForm() {
      List<FormValidator> formValidators = new ArrayList<>();
      formValidators.add(it -> {
        final Map<Element, ValidationResult> overridenValidationResults = new HashMap<>();
        String valueOfTextInput = it.get(textInput.element).getValue();
        if (valueOfTextInput.length() > 3) {
          overridenValidationResults.put(textInput.element, ValidationResult.fail("not_ok"));
        }
        return overridenValidationResults;
      });

      // test here field-apis

      Form f = new Form(formId, formValidators, new ElementContainer(protection),
          new ElementContainer(new SimpleType()), textInput,
          new ElementContainer(date, new Validator(Criteria.required())), textInput2, gender,
          new SubmitType("Submit").of(), chk, lbl, html, hddn, area, nmbr, pssword, upld, radio);

      return f;

    }


  }

}
