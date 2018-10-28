package jwebform.integration;

import java.util.Collections;
import java.util.List;
import jwebform.Form;
import jwebform.FormResult;
import jwebform.integration.beanvalidation.ExternalValidation;

// Default implementations for JWeboFormBean. Allows to override just one...
public abstract class AbstractJWebFormBean implements JWebFormBean {

  @Override
  public Form preRun(Form formTransformedFromThisBean) {
    return formTransformedFromThisBean;
  }

  @Override
  public List<ExternalValidation> validate() {
    return Collections.emptyList();
  }

  @Override
  public FormResult postRun(FormResult result) {
    return result;
  }

}
