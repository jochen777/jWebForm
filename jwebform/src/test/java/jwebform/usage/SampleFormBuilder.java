package jwebform.usage;

import jwebform.Form;
import jwebform.FormBuilder;
import jwebform.FormResult;
import jwebform.field.builder.BuildInType;
import jwebform.field.builder.FieldBuilder;
import jwebform.processor.FieldValdationResults;
import jwebform.validation.Criterion;
import jwebform.validation.FormValidator;
import jwebform.validation.ValidationResult;
import jwebform.validation.criteria.Criteria;

import java.time.LocalDate;

import static jwebform.field.builder.BuildInType.*;

public class SampleFormBuilder {

  String formId;

  public SampleFormBuilder(String id) {
    this.formId = id;
  }



  private FieldBuilder[] getTypeBuildersForSampleForm() {
    Criterion req = Criteria.required();

    // @formatter:off

    return BuildInType.array(
      
      xsrfProtectionForTesting(),
      
      simple(),
      
      text           ("textInput", "Peter\"Paul").
        criteria     (req).
        label        ("TextInputLabel"),
        
      textDate       ("dateInput", LocalDate.of(2017, 7, 4)).
        criteria     (req).
        label        ("Plase insert date").
        helpText     ("datehelptext"),
        
      text           ("textInput2", "Peter\"Paul").
        criteria     (req).
        label        ("TextInputLabel2").
        helpText     ("Help-Text").
        placeholder  ("Placeholder"),
        
      select         ("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"}).
        label        ("Gender"),
        
      submit(),
      
      checkbox       ("chk", true).
        criteria     (req).
        label        ("chk-label").
        helpText     ("chk_help"),
        
      label          ("lbl"),
      
      html           ("<strong>HTML</strong>"),
      
      hidden         ("hddn", "hddn-value"),
      
      textArea       ("area", "Area-Prebuild").
        criteria     (req).
        label        ("Area").
        helpText     ("Area-Help").
        placeholder  ("Area-Placeholder"),
        
      number         ("nbr", 5).
        criteria     (req).
        label        ("nbr-label").
        helpText     ("nrb-help"),
        
      password       ("pssword").
        label        ("Password"),
        
      upload         ("upld").
        label        ("Upload"),
        
      radio          ("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"})
        .label       ("Radio")

    );
    
 // @formatter:on
  }


  public LocalDate getDateValue(FormResult formResult) {
    return (LocalDate) formResult.getObectValue("dateInput");
  }


  public Form buildForm() {
    FormValidator validator = it -> {
      String valueOfTextInput = it.getFieldStringValue("textInput");
      if (valueOfTextInput.length() > 3) {
        return it.computeSingleFieldValidation("textInput", ValidationResult.fail("not_ok"));
      }
      return FieldValdationResults.empty();
    };


    return FormBuilder.withId(formId).validation(validator)
        .typeBuilder(getTypeBuildersForSampleForm()).build();

  }

  public Form buildWithoutUploadForm() {
    return FormBuilder.withId(formId).typeBuilder(getTypeBuildersWithoutUploadForm()).build();
  }

  private FieldBuilder[] getTypeBuildersWithoutUploadForm() {
    return BuildInType.array(
        textArea("area", "Area-Prebuild").label("Area").helpText("Area-Help")
            .placeholder("Area-Placeholder"),
        checkbox("chk", true).label("chk-label").helpText("chk_help"));
  }



}
