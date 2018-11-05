package jwebform.integration.bean2form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.integration.beanvalidation.ExternalValidation;

// if a bean implements this, these hooks will be called, when it is transformed to a form
public interface JWebFormBean {

  // the form parameter is the form that this bean was translated to. You can modify it as you want
  // here
  Form preRun(Form formTransformedFromThisBean);

  // this will be called, to validate the bean itself. (will be called after bean-validation run)
  List<ExternalValidation> validate();

  // allows to modify the formresult after the standard run process was done.
  FormResult postRun(FormResult result);

  default List<ExternalValidation> generateSingleExternalValidation(String fieldName,
      String validationMessage) {
    List<ExternalValidation> ev = new ArrayList<>();
    ev.add(new ExternalValidation(fieldName, validationMessage));
    return ev;
  }

  default List<ExternalValidation> emptyExternalValidation() {
    return Collections.emptyList();
  }
}
