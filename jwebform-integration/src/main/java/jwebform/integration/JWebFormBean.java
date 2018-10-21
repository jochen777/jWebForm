package jwebform.integration;

import jwebform.Form;
import jwebform.FormResult;

// if a bean implements this, these hooks will be called, when it is transformed to a form
public interface JWebFormBean {

  // the form parameter is the form that this bean was translated to. You can modify it as you want
  // here
  Form preRun(Form formTransformedFromThisBean);

  // allows to modify the formresult after the standard run process was done.
  FormResult postRun(FormResult result);
}
