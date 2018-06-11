package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.element.builder.Type;
import jwebform.element.builder.TypeBuilder;
import jwebform.processors.ElementValdationResults;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyFormBuilder {

  String formId;

  public MyFormBuilder(String id) {
    this.formId = id;
  }



  private TypeBuilder[] getAllTypeBuilders(boolean addUpload) {
    Criterion req = Criteria.required();


    List<TypeBuilder> els = new ArrayList<>();
    els.add(
        Type.xsrfProtectionForTesting()
    ); // no random values
    els.add(
        Type.simple()
    );
    els.add(
      Type.text("textInput", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel")
    );
    els.add(
      Type.textDate("dateInput", LocalDate.of(2017, 7, 4)).
        withCriteria(req).
        withLabel("Plase insert date").
        withHelptext("datehelptext")
    );
    els.add(
      Type.text("textInput2", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel2").
        withHelptext("Help-Text").
        withPlaceholder("Placeholder")
    );
    els.add(
      Type.select("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"})
        .withLabel("Gender")
    );
    els.add(
      Type.submit()
    );
    els.add(
      Type.checkbox("chk", true).
        withCriteria(req).withLabel("chk-label").
        withHelptext("chk_help")
    );
    els.add(
      Type.label("lbl")
    );
    els.add(
      Type.html("<strong>HTML</strong>")
    );
    els.add(
      Type.hidden("hddn", "hddn-value")
    );
    els.add(
      Type.text("area", "Area-Prebuild").
        withCriteria(req).withLabel("Area").
        withHelptext("Area-Help").
        withPlaceholder("Area-Placeholder")
    );
    els.add(
        Type.number("nbr", 5).
          withCriteria(req).
          withLabel("nbr-label").
          withHelptext("nrb-help")
    );
    els.add(
      Type.password("pssword").
        withLabel("Password")
    );
    if (addUpload) {
      els.add(
        Type.upload("upld").
          withLabel("Upload")
      );
    }
    els.add(
      Type.radio("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"})
        .withLabel("Radio")
    );


    return els.toArray(new TypeBuilder[0]);
  }


  public LocalDate getDateValue(FormResult formResult) {
    return (LocalDate) formResult.getObectValue("dateInput");
  }

  public Form buildFormWithoutUpload() {
    return buildForm(false);
  }

  public Form buildFormWithUpload() {
    return buildForm(true);
  }

  private Form buildForm(boolean withUpload) {
    FormValidator validator = it -> {
      String valueOfTextInput = it.getElementStringValue("textInput");
      if (valueOfTextInput.length() > 3) {
        return it.computeSingleElementValidation("textInput", ValidationResult.fail("not_ok"));
      }
      return ElementValdationResults.empty();
    };

    return FormBuilder.withId(formId).validation(validator)
        .typeBuilder(getAllTypeBuilders(withUpload)).build();

  }


}
