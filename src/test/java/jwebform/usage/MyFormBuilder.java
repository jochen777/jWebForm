package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import static jwebform.element.builder.Type.*;

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

    TypeBuilder [] elAr = array(
      xsrfProtectionForTesting(),
      simple(),
      text("textInput", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel"),
      textDate("dateInput", LocalDate.of(2017, 7, 4)).
        withCriteria(req).
        withLabel("Plase insert date").
        withHelptext("datehelptext"),
      hidden("sdf", "")

    );

    List<TypeBuilder> els = new ArrayList<>();
    els.add(
        xsrfProtectionForTesting()
    ); // no random values
    els.add(
        simple()
    );
    els.add(
      text("textInput", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel")
    );
    els.add(
      textDate("dateInput", LocalDate.of(2017, 7, 4)).
        withCriteria(req).
        withLabel("Plase insert date").
        withHelptext("datehelptext")
    );
    els.add(
      text("textInput2", "Peter\"Paul").
        withCriteria(req).
        withLabel("TextInputLabel2").
        withHelptext("Help-Text").
        withPlaceholder("Placeholder")
    );
    els.add(
      select("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"})
        .withLabel("Gender")
    );
    els.add(
      submit()
    );
    els.add(
      checkbox("chk", true).
        withCriteria(req).withLabel("chk-label").
        withHelptext("chk_help")
    );
    els.add(
      label("lbl")
    );
    els.add(
      html("<strong>HTML</strong>")
    );
    els.add(
      hidden("hddn", "hddn-value")
    );
    els.add(
      textArea("area", "Area-Prebuild").
        withCriteria(req).withLabel("Area").
        withHelptext("Area-Help").
        withPlaceholder("Area-Placeholder")
    );
    els.add(
      number("nbr", 5).
        withCriteria(req).
        withLabel("nbr-label").
        withHelptext("nrb-help")
    );
    els.add(
      password("pssword").
        withLabel("Password")
    );
    if (addUpload) {
      els.add(
        upload("upld").
          withLabel("Upload")
      );
    }
    els.add(
      radio("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"})
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

  public
  static <T> T[] array(T... values) { return values; }

}
