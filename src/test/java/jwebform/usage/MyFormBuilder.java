package jwebform.usage;

import jwebform.Form;
import jwebform.FormResult;
import jwebform.element.*;
import jwebform.element.builder.Type;
import jwebform.element.builder.TypeBuilder;
import jwebform.element.structure.Decoration;
import jwebform.element.structure.ElementContainer;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.Validator;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFormBuilder {

  String formId;

  public MyFormBuilder(String id) {
    this.formId = id;
  }

  XSRFProtectionType protection = new XSRFProtectionType(true); // no random values, so we can
  // expect

  Validator required = new Validator(Criteria.required());
  Criterion req = Criteria.required();

  ElementContainer textInput = new ElementContainer(new TextType("textInput", "Peter\"Paul"),
    required, new Decoration("TextInputLabel"));

  TypeBuilder textInput3 =
    Type.text("textInput", "Peter\"Paul").withLabel("TextInputLabel").withCriteria(req);

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
    return buildForm(true);
  }

  public Form buildForm(boolean withUpload) {
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
    if (withUpload) {
      return new Form(formId, formValidators, protection.of(), new SimpleType().of(), textInput,
        date, textInput2, gender, new SubmitType("Submit").of(), chk, lbl, html, hddn, area,
        nmbr, pssword, upld, radio);
    } else {
      return new Form(formId, formValidators, protection.of(), new SimpleType().of(), textInput,
        date, textInput2, gender, new SubmitType("Submit").of(), chk, lbl, html, hddn, area,
        nmbr, pssword, /* NO UPLOAD HERE! */ radio);

    }
  }


}
