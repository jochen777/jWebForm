package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import static jwebform.element.builder.Type.*;

import jwebform.element.builder.TypeBuilder;
import jwebform.processors.ElementValdationResults;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;

public class MyFormBuilder {

  String formId;

  public MyFormBuilder(String id) {
    this.formId = id;
  }



  private TypeBuilder[] getTypeBuildersForSampleForm() {
    Criterion req = Criteria.required();

    TypeBuilder [] elAr = FormBuilder.array(
      xsrfProtectionForTesting(),
      simple(),
      text("textInput", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel"),
      textDate("dateInput", LocalDate.of(2017, 7, 4)).
        withCriteria(req).
        withLabel("Plase insert date").
        withHelptext("datehelptext"),
      text("textInput2", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel2").
        withHelptext("Help-Text").
        withPlaceholder("Placeholder"),
      select("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"})
        .withLabel("Gender"),
      submit(),
      checkbox("chk", true).
        withCriteria(req).withLabel("chk-label").
        withHelptext("chk_help"),
      label("lbl"),
      html("<strong>HTML</strong>"),
      hidden("hddn", "hddn-value"),
      textArea("area", "Area-Prebuild").
        withCriteria(req).withLabel("Area").
        withHelptext("Area-Help").
        withPlaceholder("Area-Placeholder"),
      number("nbr", 5).
        withCriteria(req).
        withLabel("nbr-label").
        withHelptext("nrb-help"),
      password("pssword").
        withLabel("Password"),
      upload("upld").
        withLabel("Upload"),
      radio("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"})
        .withLabel("Radio")
    );

    return elAr;
  }


  public LocalDate getDateValue(FormResult formResult) {
    return (LocalDate) formResult.getObectValue("dateInput");
  }


  public Form buildForm() {
    FormValidator validator = it -> {
      String valueOfTextInput = it.getElementStringValue("textInput");
      if (valueOfTextInput.length() > 3) {
        return it.computeSingleElementValidation("textInput", ValidationResult.fail("not_ok"));
      }
      return ElementValdationResults.empty();
    };

    return FormBuilder.withId(formId).validation(validator)
        .typeBuilder(getTypeBuildersForSampleForm()).build();

  }

  public Form buildWithoutUploadForm() {
    return FormBuilder.withId(formId)
      .typeBuilder(getTypeBuildersWithoutUploadForm()).build();
  }

  private TypeBuilder[] getTypeBuildersWithoutUploadForm() {
    return FormBuilder.array(
      textArea("area", "Area-Prebuild").withLabel("Area").
        withHelptext("Area-Help").
        withPlaceholder("Area-Placeholder"),
      checkbox("chk", true).
        withLabel("chk-label").
        withHelptext("chk_help")
      );
  }



}
